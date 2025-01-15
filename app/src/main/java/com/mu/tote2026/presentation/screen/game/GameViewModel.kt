package com.mu.tote2026.presentation.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mu.tote2026.data.repository.Result.DEFEAT
import com.mu.tote2026.data.repository.Result.DRAW
import com.mu.tote2026.data.repository.Result.WIN
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.domain.usecase.team_usecase.TeamUseCase
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination
import com.mu.tote2026.presentation.utils.Errors.ADD_GOAL_INCORRECT
import com.mu.tote2026.presentation.utils.GROUPS
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.asTime
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.presentation.utils.generateResult
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.System.currentTimeMillis
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameUseCase: GameUseCase,
    private val teamUseCase: TeamUseCase,
    private val gamblerUseCase: GamblerUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val args = savedStateHandle.toRoute<GameDestination>()
    private val isNewGame = args.id == NEW_DOC

    var game by mutableStateOf(GameModel())
    var common by mutableStateOf(CommonParamsModel())
    private var gamblers = mutableListOf<GamblerModel>()
    private var games = mutableListOf<GameModel>()
    private var teams = listOf<TeamModel>()
    val teamList = mutableListOf<String>()
    var startTime = ""
    var startGame = false

    var exit by mutableStateOf(false)
        private set

    var isAddTime = false
        private set
    var isByPenalty = false
        private set

    var errorGoal1 = ""
        private set
    var errorGoal2 = ""
        private set
    private var errorAddGoal1 = ""
    private var errorAddGoal2 = ""
    var errorStart = ""
        private set
    var errorGameId = ""
        private set
    var errorAddTime = ""
        private set
    var errorByPenalty = ""
        private set

    var enabled = false
        private set

    var generatedGame = mutableStateOf("")

    init {
        gameUseCase.getGame(args.id).onEach { gameState ->
            _state.value = GameState(gameState)

            if (gameState is UiState.Success) {
                game = gameState.data

                if (game.start.toLong() < currentTimeMillis()
                    && game.goal1.isBlank() && game.goal2.isBlank()
                ) {
                    startGame = true
                    game = game.copy(goal1 = "0", goal2 = "0")
                }

                startTime = game.start.asTime().ifBlank { "00:00" }
                enabled = checkValues()

                gameUseCase.getGameSum().onEach { gameSumState ->
                    if (gameSumState is UiState.Success) {
                        common = gameSumState.data
                    }
                }.launchIn(viewModelScope)
                gameUseCase.getGameList().onEach { gameListState ->
                    if (gameListState is UiState.Success) {
                        games = gameListState.data.toMutableList()
                    }
                }.launchIn(viewModelScope)
                gamblerUseCase.getGamblerList().onEach { gamblerListState ->
                    if (gamblerListState is UiState.Success) {
                        gamblers = gamblerListState.data.filter { it.rate > 0 }.toMutableList()
                    }
                }.launchIn(viewModelScope)
                teamUseCase.getTeamList().onEach { teamListState ->
                    if (teamListState is UiState.Success) {
                        teams = teamListState.data
                        teams.sortedBy { it.team }.forEach { team ->
                            teamList.add(team.team)
                        }

                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnStartChange -> {
                game = game.copy(start = event.start)
                startTime = game.start.asTime()
                errorStart = checkIsFieldEmpty(event.start)
                //enabled = checkValues()
            }

            is GameEvent.OnGameIdChange -> {
                game = game.copy(id = event.id)
                errorGameId = checkIsFieldEmpty(event.id)
                //enabled = checkValues()
            }

            is GameEvent.OnGroupChange -> {
                val groupId = GROUPS.indexOf(event.group) + 1
                game = game.copy(group = event.group, groupId = groupId.toString())
                //enabled = checkValues()
            }

            is GameEvent.OnTeamChange -> {
                game = if (event.teamNo == 1)
                    game.copy(team1 = event.team, flag1 = teams.first { it.team == event.team }.flag)
                else
                    game.copy(team2 = event.team, flag2 = teams.first { it.team == event.team }.flag)
                //enabled = checkValues()
            }

            is GameEvent.OnGoalChange -> {
                checkGoal(
                    event.isAddTime,
                    event.teamNo,
                    event.goal
                )

                setResult(event.isAddTime)
                setStakePoints(event.isAddTime)

                enabled = checkValues()
            }

            is GameEvent.OnByPenaltyChange -> {
                game = game.copy(byPenalty = event.team)
                enabled = checkValues()
            }

            is GameEvent.OnSave -> {
                if (game.start.toLong() <= currentTimeMillis()) {
                    gamblers.forEach { gambler ->
                        if (game.stakes.find { it.gamblerId == gambler.id } == null) {
                            game.stakes.add(
                                StakeModel(
                                    gameId = game.id,
                                    gamblerId = gambler.id,
                                    gamblerNickname = gambler.nickname,
                                    gamblerRatePercent = gambler.ratePercent
                                )
                            )
                        }
                    }
                }
                gameUseCase.saveGame(game).onEach { gameSaveState ->
                    _state.value = when (gameSaveState) {
                        is UiState.Success -> {
                            if (game.start.toLong() <= currentTimeMillis()) {
                                val gamesCur = games.filter { it.start.toLong() <= currentTimeMillis() }
                                val gamesPrev = gamesCur.filter { it.start < game.start }

                                gamblers.forEachIndexed { idx, gambler ->
                                    val cashPrize = gamesCur.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.cashPrize ?: 0
                                    }
                                    val points = gamesCur.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.points ?: 0.0
                                    }
                                    val pointsPrev = gamesPrev.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.points ?: 0.0
                                    }
                                    gamblers[idx] = gambler.copy(
                                        pointsPrev = pointsPrev,
                                        points = points,
                                        cashPrize = cashPrize
                                    )
                                }
                                setPlaceOrPlacePrev()
                                if (gamblers.maxOf { it.pointsPrev > 0.0 }) setPlaceOrPlacePrev(true)

                                gamblers.forEach { gambler ->
                                    gamblerUseCase.saveGambler(gambler).launchIn(viewModelScope)
                                }
                            }

                            exit = true
                            GameState(UiState.Success(game))
                        }

                        is UiState.Loading -> GameState(UiState.Loading)
                        is UiState.Error -> GameState(UiState.Error(gameSaveState.error))
                        else -> GameState(UiState.Default)
                    }
                }.launchIn(viewModelScope)
            }

            is GameEvent.OnGenerateGame -> {
                generatedGame.value = generateResult()
            }
        }
    }

    private fun setPlaceOrPlacePrev(prev: Boolean = false) {
        var place = 1
        var pointsCur = Double.MAX_VALUE
        var step = 0
        gamblers
            .sortedByDescending { if (!prev) it.points else it.pointsPrev }
            .forEachIndexed { idx, gambler ->
                if ((if (!prev) gambler.points else gambler.pointsPrev) < pointsCur) {
                    pointsCur = (if (!prev) gambler.points else gambler.pointsPrev)
                    place += step
                    step = 1
                } else {
                    step++
                }
                gamblers[idx] = if (!prev) gambler.copy(place = place) else gambler.copy(placePrev = place)
                /*gamblerUseCase.saveGambler(if (!prev) gambler.copy(place = place) else gambler.copy(placePrev = place))
                    .launchIn(scope)*/
            }
    }

    private fun setResult(isAddTime: Boolean) {
        val result: String
        if (!isAddTime) {
            result = game.result
            game = game.copy(
                result = if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
                    when {
                        game.goal1.toInt() > game.goal2.toInt() -> WIN
                        game.goal1.toInt() == game.goal2.toInt() -> DRAW
                        game.goal1.toInt() < game.goal2.toInt() -> DEFEAT
                        else -> result
                    }
                } else result
            )
        } else {
            result = game.addResult
            game = game.copy(
                addResult = if (game.addGoal1.isNotBlank() && game.addGoal2.isNotBlank()) {
                    when {
                        game.addGoal1.toInt() > game.addGoal2.toInt() -> WIN
                        game.addGoal1.toInt() == game.addGoal2.toInt() -> DRAW
                        game.addGoal1.toInt() < game.addGoal2.toInt() -> DEFEAT
                        else -> result
                    }
                } else result
            )
        }
    }

    private fun setStakePoints(isAddTime: Boolean) {
        val stakes = game.stakes
        var pointsSum = 0.0
        var addPointsSum = 0.0
        stakes.forEachIndexed { idx, stake ->
            if (!isAddTime) {
                val points = calcMainPoints(game, stake)
                pointsSum += (points * stake.gamblerRatePercent)
                game.stakes[idx] = game.stakes[idx].copy(points = points)
            } else {
                val addPoints = calcAddPoints(game, stake)
                addPointsSum += (addPoints * stake.gamblerRatePercent)
                game.stakes[idx] = game.stakes[idx].copy(addPoints = addPoints)
            }
        }

        val coefficient = if (game.groupId.toInt() <= GROUPS_COUNT)
            common.groupGameSum / pointsSum
        else
            common.playoffGameSum / addPointsSum

        stakes.forEachIndexed { idx, _ ->
            val cash = if (game.stakes[idx].result.isNotBlank())
                (game.stakes[idx].points + game.stakes[idx].addPoints) * game.stakes[idx].gamblerRatePercent * coefficient
            else 0.0

            //val cash = (game.stakes[idx].points * game.stakes[idx].gamblerRatePercent * coefficient)
            game.stakes[idx] = game.stakes[idx].copy(cashPrize = round(cash).toInt())
        }
    }

    private fun calcMainPoints(game: GameModel, stake: StakeModel): Double {
        if (stake.result.isBlank()) return stake.points

        var points = 0.0
        if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
            if (game.result == stake.result) {
                points = when (game.result) {
                    WIN -> game.winCoefficient
                    DRAW -> game.drawCoefficient
                    DEFEAT -> game.defeatCoefficient
                    else -> 0.0
                }
            }
            points += if (game.goal1 == stake.goal1 && game.goal2 == stake.goal2) {
                (points / 2.0)
            } else if (game.result != DRAW
                && ((game.goal1.toInt() - game.goal2.toInt()) == (stake.goal1.toInt() - stake.goal2.toInt()))
            ) {
                0.25
            } else if (game.goal1 == stake.goal1 || game.goal2 == stake.goal2) {
                0.1
            } else {
                0.0
            }
        }
        return points
    }

    private fun calcAddPoints(game: GameModel, stake: StakeModel): Double {
        if (stake.addResult.isBlank()) return stake.addPoints

        var addPoints = 0.0
        if (game.addGoal1.isNotBlank() && game.addGoal2.isNotBlank()) {
            addPoints += if (game.addResult == stake.addResult) {
                1.0 + if (game.addGoal1 == stake.addGoal1 && game.addGoal2 == stake.addGoal2) 0.5
                else 0.0
            } else 0.0

            addPoints += if (game.addResult != DRAW
                && ((game.addGoal1.toInt() - game.addGoal2.toInt()) == (stake.addGoal1.toInt() - stake.addGoal2.toInt()))
            ) {
                0.25
            } else if (game.addGoal1 == stake.addGoal1 || game.addGoal2 == stake.addGoal2) 0.01
            else 0.0

            addPoints += if (game.byPenalty.isNotBlank() && game.byPenalty == stake.byPenalty) 1.0 else 0.0

            /*addPoints += if (game.addResult == stake.addResult) {
                (1.0 + if (game.addGoal1 == stake.addGoal1 && game.addGoal2 == stake.addGoal2) 0.5
                else 0.0)
            } else if (game.addResult != DRAW) {
                if ((game.addGoal1.toInt() - game.addGoal2.toInt()) == (stake.addGoal1.toInt() - stake.addGoal2.toInt())) 0.25
                else if (game.addGoal1 == stake.addGoal1 || game.addGoal2 == stake.addGoal2) 0.01
                else 0.0
            } else if (game.byPenalty.isNotBlank() && game.byPenalty == stake.byPenalty) 1.0
            else 0.0*/
        }
        return addPoints
    }

    private fun checkGoal(addTime: Boolean, teamNo: Int, goal: String) {
        if (!addTime) {
            if (teamNo == 1) {
                game = game.copy(goal1 = goal)
                errorGoal1 = if (isNewGame) "" else checkIsFieldEmpty(goal)
            } else {
                game = game.copy(goal2 = goal)
                errorGoal2 = if (isNewGame) "" else checkIsFieldEmpty(goal)
            }
        } else {
            if (teamNo == 1) {
                game = game.copy(addGoal1 = goal)
                errorAddGoal1 = checkIsFieldEmpty(goal).ifBlank {
                    if (game.addGoal1 < game.goal1)
                        ADD_GOAL_INCORRECT
                    else ""
                }
            } else {
                game = game.copy(addGoal2 = goal)
                errorAddGoal2 = checkIsFieldEmpty(goal).ifBlank {
                    if (game.addGoal2 < game.goal2)
                        ADD_GOAL_INCORRECT
                    else ""
                }
            }
            errorAddTime = errorAddGoal1.ifBlank { errorAddGoal2 }
        }
    }

    private fun checkMainTime(): Boolean =
        if (game.id.isNotBlank()
            && game.start.isNotBlank()
            && game.group.isNotBlank()
            && game.team1.isNotBlank()
            && game.team2.isNotBlank()
        ) {
            /*if (isNewGame) {
                true*/
            if (game.start.toLong() > currentTimeMillis()) {
                true
            }else if (startGame) {
                setResult(false)
                setStakePoints(false)
                true
            } else {
                if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
                    isAddTime = (GROUPS.indexOf(game.group) >= GROUPS_COUNT
                            && game.goal1 == game.goal2)
                    if (!isAddTime) {
                        game = game.copy(
                            addGoal1 = "",
                            addGoal2 = "",
                            byPenalty = "",
                        )
                    }
                    true
                } else {
                    false
                }
            }
        } else {
            false
        }


    private fun checkAddTime(): Boolean {
        var result = (game.addGoal1.isNotBlank() && (game.addGoal1 >= game.goal1))
        result = (result && (game.addGoal2.isNotBlank()) && (game.addGoal2 >= game.goal2))

        isByPenalty = result && (game.addGoal1 == game.addGoal2)

        if (isByPenalty) {
            result = game.byPenalty.isNotBlank()
        } else {
            game = game.copy(byPenalty = "")
        }

        return result
    }

    private fun checkValues(): Boolean {
        isAddTime = false
        isByPenalty = false

        var result = checkMainTime()

        if (isAddTime) {
            result = result && checkAddTime()
        }

        return result
    }

    data class GameState(
        val result: UiState<GameModel> = UiState.Default
    )
}