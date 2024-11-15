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
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    var exit by mutableStateOf(false)
        private set

    init {
        val args = savedStateHandle.toRoute<GameDestination>()
        gameUseCase.getGame(args.id).onEach { gameState ->
            _state.value = GameState(gameState)
        }.launchIn(viewModelScope)

    }

    data class GameState(
        val result: UiState<GameModel> = UiState.Default
    )
}