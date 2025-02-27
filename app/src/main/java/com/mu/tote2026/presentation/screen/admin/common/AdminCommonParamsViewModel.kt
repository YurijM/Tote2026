package com.mu.tote2026.presentation.screen.admin.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminCommonParamsViewModel @Inject constructor(
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminCommonParamsState())
    var state = _state.asStateFlow()

    var commonParams by mutableStateOf(PrizeFundModel())
        private set

    init {
        gamblerUseCase.getPrizeFund().onEach { commonParamsState ->
            _state.value = AdminCommonParamsState(commonParamsState)

            if (commonParamsState is UiState.Success) {
                commonParams = commonParamsState.data
            }
        }.launchIn(viewModelScope)
    }

    data class AdminCommonParamsState(
        val result: UiState<PrizeFundModel> = UiState.Default
    )
}