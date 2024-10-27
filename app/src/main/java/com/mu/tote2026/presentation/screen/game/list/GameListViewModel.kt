package com.mu.tote2026.presentation.screen.game.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.utils.BRUSH
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class TeamResultModel(
    val group: String = "",
    val team: String = "",
    val teamNo: Int = 0,
    val score1: String = "",
    val score2: String = "",
    val score3: String = "",
    val score4: String = "",
    val win: Int = 0,
    val draw: Int = 0,
    val defeat: Int = 0,
    val balls1: Int = 0,
    val balls2: Int = 0,
    val points: Int = 0,
    val place: Int = 0,
)

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameListState())
    val state = _state.asStateFlow()

    private var games = mutableListOf<GameModel>()
    var groupResult = arrayListOf<List<TeamResultModel>>()
        private set

    init {
        gameUseCase.getGameList().onEach { gameListState ->
            if (gameListState is UiState.Success) {
                games = gameListState.data.toMutableList()
                for (groupId in 1..GROUPS_COUNT) {
                    val groupGames = games.filter { it.groupId.toInt() == groupId }
                    val resultList = mutableListOf<TeamResultModel>()

                    for (itemNo in 1..4) {
                        val teamResult = groupGames
                            .filter { it.team1ItemNo.toInt() == itemNo || it.team2ItemNo.toInt() == itemNo }
                            .sortedBy {
                                if (it.team1ItemNo.toInt() == itemNo)
                                    it.team2ItemNo
                                else
                                    it.team1ItemNo
                            }

                        var result = TeamResultModel()
                        var balls1 = 0
                        var balls2 = 0
                        var win = 0
                        var draw = 0
                        var defeat = 0
                        var points = 0

                        teamResult.forEachIndexed { index, game ->
                            val isFirstTeam = game.team1ItemNo.toInt() == itemNo

                            if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
                                if (isFirstTeam) {
                                    balls1 += game.goal1.toInt()
                                    balls2 += game.goal2.toInt()
                                    when {
                                        (game.goal1.toInt() > game.goal2.toInt()) -> {
                                            win++
                                            points += 3
                                        }

                                        (game.goal1.toInt() == game.goal2.toInt()) -> {
                                            draw++
                                            points += 1
                                        }

                                        else -> defeat++
                                    }
                                } else {
                                    balls1 += game.goal2.toInt()
                                    balls2 += game.goal1.toInt()
                                    when {
                                        (game.goal2.toInt() > game.goal1.toInt()) -> {
                                            win++
                                            points += 3
                                        }

                                        (game.goal2.toInt() == game.goal1.toInt()) -> {
                                            draw++
                                            points += 1
                                        }

                                        else -> defeat++
                                    }
                                }
                            }

                            if (index == 0) {
                                result = TeamResultModel(
                                    group = game.group,
                                    team = if (isFirstTeam) game.team1 else game.team2,
                                    teamNo = itemNo,
                                )
                            }

                            result = when (itemNo) {
                                1 -> result.copy(score1 = BRUSH)
                                2 -> result.copy(score2 = BRUSH)
                                3 -> result.copy(score3 = BRUSH)
                                4 -> result.copy(score4 = BRUSH)
                                else -> result
                            }

                        }
                        result = result.copy(
                            win = win,
                            draw = draw,
                            defeat = defeat,
                            balls1 = balls1,
                            balls2 = balls2,
                            points = points
                        )
                        resultList.add(result)
                    }
                    groupResult.add(resultList)
                }
                /*groupResult.forEach { result ->
                    result.forEach { teamResult ->
                        toLog("teamResult ${teamResult.group}: $teamResult")
                    }
                    toLog("\n\n")
                }*/
                _state.value = GameListState(gameListState)
            } else
                _state.value = GameListState(gameListState)
        }.launchIn(viewModelScope)
    }

    companion object {
        data class GameListState(
            val result: UiState<List<GameModel>> = UiState.Default
        )
    }
}