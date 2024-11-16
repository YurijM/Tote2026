package com.mu.tote2026.presentation.screen.game.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<GamesState> = MutableStateFlow(GamesState())
    val state = _state.asStateFlow()

    init {
        gameUseCase.getGameList().onEach { gameState ->
            _state.value = GamesState(gameState)

            if (gameState is UiState.Success) {
                _state.value = GamesState(
                    UiState.Success(gameState.data)
                )
            }
        }.launchIn(viewModelScope)
    }

    data class GamesState(
        val result: UiState<List<GameModel>> = UiState.Default
    )
}