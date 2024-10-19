package com.mu.tote2026.presentation.screen.stake

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.KEY_GAMBLER_ID
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.NEW_DOC
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
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StakeState())
    val state = _state.asStateFlow()

    var game by mutableStateOf(GameModel())
        private set
    var stake by mutableStateOf(StakeModel())
        private set

    var isExtraTime = false
        private set
    var isByPenalty = false
        private set

    var errorMainTime = ""
        private set
    var errorExtraTime = ""
        private set
    var errorByPenalty = ""
        private set

    init {
        val gameId = savedStateHandle.get<String>(KEY_ID) ?: NEW_DOC
        val gamblerId = savedStateHandle.get<String>(KEY_GAMBLER_ID) ?: NEW_DOC

        gameUseCase.getGamblerGameStake(gameId, gamblerId).onEach { gameState ->
            _state.value = StakeState(gameState)

            if (gameState is UiState.Success) {
                game = gameState.data

                stake = if (game.stakes.isNotEmpty())
                    game.stakes[0]
                else
                    StakeModel(gameId, gamblerId)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: StakeEvent) {
        when (event) {
            is StakeEvent.OnGoal1Change -> {
                stake = stake.copy(goal1 = event.goal)
            }
            else -> {}
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

    companion object {
        data class StakeState (
            val result: UiState<GameModel> = UiState.Default
        )
    }
}