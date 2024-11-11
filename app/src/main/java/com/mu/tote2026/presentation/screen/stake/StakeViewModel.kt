package com.mu.tote2026.presentation.screen.stake

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.navigation.Destinations.StakeRoute
import com.mu.tote2026.presentation.utils.Errors.ADD_GOAL_INCORRECT
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StakeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StakeState())
    val state = _state.asStateFlow()

    var game by mutableStateOf(GameModel())
        private set
    var stake by mutableStateOf(StakeModel())
        private set
    private var oldStake = StakeModel()

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

    init {
        val args = savedStateHandle.toRoute<StakeRoute>()
        toLog("args: $args")
        gameUseCase.getGamblerGameStake(args.gameId, args.gamblerId).onEach { gameState ->
            _state.value = StakeState(gameState)

            if (gameState is UiState.Success) {
                game = gameState.data

                stake = if (game.stakes.isNotEmpty())
                    game.stakes[0]
                else
                    StakeModel(args.gameId, args.gamblerId)

                oldStake = stake

                enabled = checkValues()
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: StakeEvent) {
        when (event) {
            is StakeEvent.OnGoalChange -> {
                checkGoal(
                    extraTime = event.extraTime,
                    teamNo = event.teamNo,
                    goal = event.goal
                )
                enabled = checkValues()
            }

            is StakeEvent.OnSave -> {
                gameUseCase.saveStake(oldStake, stake).onEach { saveStakeState ->
                    _state.value = when (saveStakeState) {
                        is UiState.Success -> {
                            exit = true
                            StakeState(UiState.Success(game))
                        }
                        is UiState.Loading -> StakeState(UiState.Loading)
                        is UiState.Error -> StakeState(UiState.Error(saveStakeState.error))
                        else -> StakeState(UiState.Default)
                    }
                }.launchIn(viewModelScope)
            }

            else -> {}
        }
    }

    private fun checkGoal(extraTime: Boolean, teamNo: Int, goal: String) {
        if (!extraTime) {
            if (teamNo == 1) {
                stake = stake.copy(goal1 = goal)
                errorGoal1 = checkIsFieldEmpty(goal)
            } else {
                stake = stake.copy(goal2 = goal)
                errorGoal2 = checkIsFieldEmpty(goal)
            }
            errorMainTime = errorGoal1.ifBlank { errorGoal2 }
        } else {
            if (teamNo == 1) {
                stake = stake.copy(addGoal1 = goal)
                errorAddGoal1 = checkIsFieldEmpty(goal).ifBlank {
                    if (stake.addGoal1 < stake.goal1)
                        ADD_GOAL_INCORRECT
                    else ""
                }
            } else {
                stake = stake.copy(addGoal2 = goal)
                errorAddGoal2 = checkIsFieldEmpty(goal).ifBlank {
                    if (stake.addGoal2 < stake.goal2)
                        ADD_GOAL_INCORRECT
                    else ""
                }
            }
            errorExtraTime = errorAddGoal1.ifBlank { errorAddGoal2 }
        }
    }

    private fun checkMainTime(): Boolean =
        if (stake.goal1.isNotBlank()
            && stake.goal2.isNotBlank()
        ) {
            isExtraTime = (game.groupId.isNotBlank()
                    && game.groupId.toInt() >= GROUPS_COUNT
                    && stake.goal1 == stake.goal2)
            if (!isExtraTime) {
                stake = stake.copy(
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
        var result = if (stake.addGoal1.isNotBlank())
            stake.addGoal1 >= stake.goal1
        else
            true

        result = result && if (stake.addGoal2.isNotBlank())
            stake.addGoal2 >= stake.goal2
        else
            true

        isByPenalty = (stake.addGoal1.isNotBlank() && stake.addGoal1 >= stake.goal1
                && stake.addGoal2.isNotBlank() && stake.addGoal2 >= stake.goal2
                && stake.addGoal1 == stake.addGoal2)

        if (isByPenalty) {
            result = result && stake.byPenalty.isNotBlank()
        } else {
            stake = stake.copy(byPenalty = "")
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

    companion object {
        data class StakeState(
            val result: UiState<GameModel> = UiState.Default
        )
    }
}