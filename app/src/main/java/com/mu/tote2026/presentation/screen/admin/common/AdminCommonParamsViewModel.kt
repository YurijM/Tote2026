package com.mu.tote2026.presentation.screen.admin.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.usecase.common_params_usecase.CommonParamsUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminCommonParamsViewModel @Inject constructor(
    commonParamsUseCase: CommonParamsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminCommonParamsState())
    var state = _state.asStateFlow()

    var commonParams by mutableStateOf(CommonParamsModel())
    var exit by mutableStateOf(false)

    init {
        commonParamsUseCase.getCommonParams().onEach { commonParamsState ->
            _state.value = AdminCommonParamsState(commonParamsState)

            if (commonParamsState is UiState.Success) {
                commonParams = commonParamsState.data
            }
        }.launchIn(viewModelScope)
    }

    data class AdminCommonParamsState(
        val result: UiState<CommonParamsModel> = UiState.Default
    )
}