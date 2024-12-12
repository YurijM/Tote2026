package com.mu.tote2026.presentation.screen.prognosis

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
class PrognosisViewModel @Inject constructor(
    gameUseCase: GameUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(PrognosisState())
    val state = _state.asStateFlow()

    init {
        gameUseCase.getGameList().onEach { prognosisState ->
            _state.value = PrognosisState(prognosisState)

            if (prognosisState is UiState.Success) {
                _state.value = PrognosisState(
                    UiState.Success(prognosisState.data.filter { it.id.toInt() <= 2 }.sortedByDescending { it.id.toInt() })
                )
            }
        }.launchIn(viewModelScope)
    }

    data class PrognosisState(
        val result: UiState<List<GameModel>> = UiState.Default
    )
}