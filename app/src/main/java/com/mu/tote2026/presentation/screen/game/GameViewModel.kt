package com.mu.tote2026.presentation.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination
import com.mu.tote2026.presentation.utils.Errors.ADD_GOAL_INCORRECT
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.presentation.utils.generateResult
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    var game by mutableStateOf(GameModel())

    var exit by mutableStateOf(false)
        private set

    var isExtraTime = false
        private set
    var isByPenalty = false
        private set

    private var errorGoal1 = ""
    private var errorGoal2 = ""
    private var errorAddGoal1 = ""
    private var errorAddGoal2 = ""

    var errorMainTime = ""
        private set
    var errorExtraTime = ""
        private set
    var errorByPenalty = ""
        private set

    var enabled = false
        private set

    var generatedGame = mutableStateOf("")

    init {
        val args = savedStateHandle.toRoute<GameDestination>()
        gameUseCase.getGame(args.id).onEach { gameState ->
            _state.value = GameState(gameState)

            if (gameState is UiState.Success) {
                game = gameState.data
                enabled = checkValues()
            }
        }.launchIn(viewModelScope)

    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnGoalChange -> {
                checkGoal(
                    event.extraTime,
                    event.teamNo,
                    event.goal
                )
                enabled = checkValues()
            }

            is GameEvent.OnByPenaltyChange -> {
                game = game.copy(byPenalty = event.team)
                enabled = checkValues()
            }

            is GameEvent.OnSave -> {
                gameUseCase.saveGame(game).onEach { gameSaveState ->
                    _state.value = when (gameSaveState) {
                        is UiState.Success -> {
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

    private fun checkGoal(extraTime: Boolean, teamNo: Int, goal: String) {
        if (!extraTime) {
            if (teamNo == 1) {
                game = game.copy(goal1 = goal)
                errorGoal1 = checkIsFieldEmpty(goal)
            } else {
                game = game.copy(goal2 = goal)
                errorGoal2 = checkIsFieldEmpty(goal)
            }
            errorMainTime = errorGoal1.ifBlank { errorGoal2 }
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
            errorExtraTime = errorAddGoal1.ifBlank { errorAddGoal2 }
        }
    }

    private fun checkMainTime(): Boolean =
        if (game.goal1.isNotBlank()
            && game.goal2.isNotBlank()
        ) {
            isExtraTime = (game.groupId.isNotBlank()
                    && game.groupId.toInt() >= GROUPS_COUNT
                    && game.goal1 == game.goal2)
            if (!isExtraTime) {
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

    private fun checkExtraTime(): Boolean {
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
        isExtraTime = false
        isByPenalty = false

        var result = checkMainTime()

        if (isExtraTime) {
            result = result && checkExtraTime()
        }

        return result
    }

    data class GameState(
        val result: UiState<GameModel> = UiState.Default
    )
}