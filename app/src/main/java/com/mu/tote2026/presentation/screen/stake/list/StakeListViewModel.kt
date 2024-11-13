package com.mu.tote2026.presentation.screen.stake.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
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
class StakeListViewModel @Inject constructor(
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StakeListState())
    val state = _state.asStateFlow()

    init {
        gameUseCase.getGamblerStakes(CURRENT_ID).onEach { stakeListState ->
            _state.value = StakeListState(stakeListState)

            if (stakeListState is UiState.Success) {
                _state.value = StakeListState(
                    UiState.Success(stakeListState.data)
                )
            }
        }.launchIn(viewModelScope)
    }

    data class StakeListState(
        val result: UiState<List<GameModel>> = UiState.Default
    )
}