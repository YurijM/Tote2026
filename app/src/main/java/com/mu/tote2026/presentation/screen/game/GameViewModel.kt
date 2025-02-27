package com.mu.tote2026.presentation.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mu.tote2026.data.repository.GROUP_GAMES_COUNT
import com.mu.tote2026.data.repository.PLAYOFF_GAMES_COUNT
import com.mu.tote2026.data.repository.Result.DEFEAT
import com.mu.tote2026.data.repository.Result.DRAW
import com.mu.tote2026.data.repository.Result.WIN
import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.model.WinnerModel
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
    private var commonParams by mutableStateOf(PrizeFundModel())
    private var gamblers = mutableListOf<GamblerModel>()
    private var prevWinners = listOf<WinnerModel>()
    private var games = mutableListOf<GameModel>()
    private var teams = listOf<TeamModel>()
    val teamList = mutableListOf<String>()
    var startTime = ""

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

                startTime = game.start.asTime().ifBlank { "00:00" }
                enabled = checkValues()

                gamblerUseCase.getPrizeFund().onEach { prizeFundState ->
                    if (prizeFundState is UiState.Success) {
                        commonParams = prizeFundState.data

                        /*if (game.start.toLong() < currentTimeMillis()
                            && game.goal1.isBlank() && game.goal2.isBlank()
                        ) {
                            game = game.copy(goal1 = "0", goal2 = "0")
                            setResult(false)
                            setStakePoints(false)
                            enabled = true
                        }*/
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
                gamblerUseCase.getWinners().onEach { winnersState ->
                    if (winnersState is UiState.Success) {
                        prevWinners = winnersState.data
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
                enabled = checkValues()
            }

            is GameEvent.OnGameIdChange -> {
                game = game.copy(id = event.id)
                errorGameId = checkIsFieldEmpty(event.id)
                enabled = checkValues()
            }

            is GameEvent.OnGroupChange -> {
                val groupId = GROUPS.indexOf(event.group) + 1
                game = game.copy(group = event.group, groupId = groupId.toString())
                enabled = checkValues()
            }

            is GameEvent.OnTeamChange -> {
                game = if (event.teamNo == 1)
                    game.copy(team1 = event.team, flag1 = teams.first { it.team == event.team }.flag)
                else
                    game.copy(team2 = event.team, flag2 = teams.first { it.team == event.team }.flag)
                enabled = checkValues()
            }

            is GameEvent.OnGoalChange -> {
                checkGoal(
                    event.isAddTime,
                    event.teamNo,
                    event.goal
                )

                setResult(event.isAddTime)
                //setStakePoints(event.isAddTime)
                setStakePoints()

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

                                // Удаление сумм дополнительных выигрышей у предыдущих победителей
                                deletingCashPrizePrevWinners()

                                gamblers.forEachIndexed { idx, gambler ->
                                    val cashPrize = gamesCur.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.cashPrize ?: 0.0
                                    }
                                    val points = gamesCur.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.points ?: 0.0
                                    } + gamesCur.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.addPoints ?: 0.0
                                    }
                                    val pointsPrev = gamesPrev.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.points ?: 0.0
                                    } + gamesPrev.sumOf {
                                        it.stakes.find { stake -> stake.gamblerId == gambler.id }?.addPoints ?: 0.0
                                    }
                                    gamblers[idx] = gambler.copy(
                                        pointsPrev = pointsPrev,
                                        points = points,
                                        cashPrize = cashPrize
                                    )
                                }
                                setPlaceOrPlacePrev()
                                if (gamblers.maxOf { it.pointsPrev > 0.0 }) setPlaceOrPlacePrev(true)

                                gamblerUseCase.deleteWinners().onEach { deleteWinnersState ->
                                    if (deleteWinnersState is UiState.Success) {
                                        setWinnerCashPrize()

                                        //val winners = gamblers.filter { it.place <= 3 }.sortedBy { it.place }
                                        //val percentSum = winners.sumOf { it.ratePercent }
                                        //var placePrizeFund = 0.0

                                        /*val winners1 = winners.filter { it.place == 1 }
                                        if (winners1.isNotEmpty()) {
                                            placePrizeFund = if (winners1.count() >= 3) {
                                                commonParams.winnersPrizeFund
                                            } else {
                                                commonParams.place1PrizeFund
                                            } / winners1.count().toDouble()

                                            setWinnerCashPrize(winners1, percentSum, placePrizeFund)
                                        }

                                        val winners2 = winners.filter { it.place == 2 }
                                        if (winners2.isNotEmpty()) {
                                            placePrizeFund = if (winners2.count() >= 2) {
                                                (commonParams.place2PrizeFund + commonParams.place3PrizeFund)
                                            } else {
                                                commonParams.place2PrizeFund
                                            } / winners2.count().toDouble()

                                            setWinnerCashPrize(winners2, percentSum, placePrizeFund)
                                        }

                                        val winners3 = winners.filter { it.place == 3 }
                                        if (winners3.isNotEmpty()) {
                                            setWinnerCashPrize(
                                                winners3,
                                                percentSum,
                                                commonParams.place3PrizeFund / winners3.count()
                                            )
                                        }*/

                                        gamblers.forEach { gambler ->
                                            gamblerUseCase.saveGambler(gambler).launchIn(viewModelScope)
                                        }
                                    }
                                }.launchIn(viewModelScope)
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

    private fun setStakePoints() {
        //val stakes = game.stakes
        var pointsSum = 0.0

        game.stakes.forEachIndexed { idx, stake ->
            var points = calcMainPoints(game, stake)
            game.stakes[idx] = stake.copy(points = points)

            pointsSum += (points * stake.gamblerRatePercent)

            points = calcAddPoints(game, stake)
            game.stakes[idx] = game.stakes[idx].copy(addPoints = points)

            pointsSum += (points * stake.gamblerRatePercent)
        }

        val coefficient = if (game.groupId.toInt() <= GROUPS_COUNT) {
            if (pointsSum > 0) commonParams.groupPrizeFund / GROUP_GAMES_COUNT.toDouble() / pointsSum else 0.0
        } else {
            if (pointsSum > 0) commonParams.playoffPrizeFund / PLAYOFF_GAMES_COUNT.toDouble() / pointsSum else 0.0
        }

        game.stakes.forEachIndexed { idx, stake ->
            val cash = if (stake.result.isNotBlank())
                (stake.points + stake.addPoints) * coefficient * stake.gamblerRatePercent
            else 0.0

            //game.stakes[idx] = stake.copy(cashPrize = round(cash).toInt())
            game.stakes[idx] = stake.copy(cashPrize = cash)
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
            } else if (game.addGoal1 == stake.addGoal1 || game.addGoal2 == stake.addGoal2) 0.1
            else 0.0

            addPoints += if (game.byPenalty.isNotBlank() && game.byPenalty == stake.byPenalty) {
                1.0
            } else {
                0.0
            }
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

    /*private fun checkMainTime(): Boolean =
        if (game.id.isNotBlank()
            && game.start.isNotBlank()
            && game.group.isNotBlank()
            && game.team1.isNotBlank()
            && game.team2.isNotBlank()
        ) {
            if (game.start.toLong() > currentTimeMillis()) {
                true
            } else {
                if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
                    isAddTime = (game.groupId.toInt() > GROUPS_COUNT
                            && game.goal1 == game.goal2)
                    if (!isAddTime) {
                        game = game.copy(
                            addGoal1 = "",
                            addGoal2 = "",
                            addResult = "",
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
        }*/

    /*private fun checkAddTime(): Boolean {
        var result = (game.addGoal1.isNotBlank() && (game.addGoal1 >= game.goal1))
        result = (result && (game.addGoal2.isNotBlank()) && (game.addGoal2 >= game.goal2))

        isByPenalty = result && (game.addGoal1 == game.addGoal2)

        if (isByPenalty) {
            result = game.byPenalty.isNotBlank()
        } else {
            game = game.copy(byPenalty = "")
        }

        return result
    }*/

    private fun checkValues(): Boolean {
        isAddTime = false
        isByPenalty = false

        //return checkMainTime()

        var check = false

        if (game.id.isNotBlank()
            && game.start.isNotBlank()
            && game.group.isNotBlank()
            && game.team1.isNotBlank()
            && game.team2.isNotBlank()
        ) {
            if (game.start.toLong() > currentTimeMillis()) {
                check = true
            } else {
                if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
                    isAddTime = (game.groupId.toInt() > GROUPS_COUNT
                            && game.goal1 == game.goal2)
                    if (!isAddTime) {
                        game = game.copy(
                            addGoal1 = "",
                            addGoal2 = "",
                            addResult = "",
                            byPenalty = "",
                        )
                    } else {
                        if (game.goal1.isNotBlank() && game.goal2.isNotBlank()
                            && game.addGoal1 == game.addGoal2
                        )
                            isByPenalty = true
                        else
                            game = game.copy(byPenalty = "")
                    }
                    check = true
                }
            }
        }

        return check
    }

    /*private fun setWinnerCashPrize(
        winners: List<GamblerModel>,
        percentSum: Double,
        placePrizeFund: Double
    ) {
        winners.forEach { gambler ->
            val cashPrizeByStake = (commonParams.winnersPrizeFundByStake * gambler.ratePercent) / percentSum
            val winner = WinnerModel(
                gamblerId = gambler.id,
                gamblerNickname = gambler.nickname,
                cashPrize = placePrizeFund,
                cashPrizeByStake = cashPrizeByStake
            )

            val idx = gamblers.indexOf(gambler)
            gamblers[idx] =
                gamblers[idx].copy(cashPrize = round(gambler.cashPrize + placePrizeFund + cashPrizeByStake).toInt())

            gamblerUseCase.saveWinner(winner).launchIn(viewModelScope)
        }
    }*/

    private fun setWinnerCashPrize() {
        val winners = gamblers.filter { it.place <= 3 }.sortedBy { it.place }
        val percentSum = winners.sumOf { it.ratePercent }
        val winnerFirst = winners.first()
        val winnerLast = winners.last()

        val coefficientWinner1: Double
        var coefficientWinner2 = 0.0
        var coefficientWinner3 = 0.0

        when (winnerLast.place) {
            3 -> {
                coefficientWinner3 = 1.0
                coefficientWinner1 = winnerFirst.points - winnerLast.points + 1.0
                coefficientWinner2 = winners.first { it.place == 2 }.points - winnerLast.points + 1.0
            }
            2 -> {
                coefficientWinner2 = 1.0
                coefficientWinner1 = winnerFirst.points - winnerLast.points + 1.0
            }
            else -> coefficientWinner1 = 1.0
        }

        val coefficientSum = coefficientWinner1 * winners.filter { it.place == 1 }.size +
                coefficientWinner2 * winners.filter { it.place == 2 }.size +
                coefficientWinner3 * winners.filter { it.place == 3 }.size

        winners.forEach { gambler ->
            val placePrizeFund = commonParams.winnersPrizeFund / coefficientSum *
                    when (gambler.place) {
                        1 -> coefficientWinner1
                        2 -> coefficientWinner2
                        else -> coefficientWinner3
                    }

            val cashPrizeByStake = (commonParams.winnersPrizeFundByStake * gambler.ratePercent) / percentSum

            val winner = WinnerModel(
                gamblerId = gambler.id,
                gamblerNickname = gambler.nickname,
                cashPrize = placePrizeFund,
                cashPrizeByStake = cashPrizeByStake
            )

            val idx = gamblers.indexOf(gambler)
            gamblers[idx] =
                //gamblers[idx].copy(cashPrize = round(gambler.cashPrize + placePrizeFund + cashPrizeByStake).toInt())
                gamblers[idx].copy(cashPrize = gambler.cashPrize + placePrizeFund + cashPrizeByStake)

            gamblerUseCase.saveWinner(winner).launchIn(viewModelScope)
        }
    }

    private fun deletingCashPrizePrevWinners() {
        prevWinners.forEach { winner ->
            val gambler = gamblers.find { it.id == winner.gamblerId }
            if (gambler != null) {
                val cashPrize = gambler.cashPrize - winner.cashPrize - winner.cashPrizeByStake
                val idx = gamblers.indexOf(gambler)
                //gamblers[idx] = gamblers[idx].copy(cashPrize = round(cashPrize).toInt())
                gamblers[idx] = gamblers[idx].copy(cashPrize = cashPrize)
            }
        }
    }

    data class GameState(
        val result: UiState<GameModel> = UiState.Default
    )
}
