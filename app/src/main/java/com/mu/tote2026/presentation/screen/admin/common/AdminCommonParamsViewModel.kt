package com.mu.tote2026.presentation.screen.admin.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.usecase.common_params_usecase.CommonParamsUseCase
import com.mu.tote2026.presentation.utils.Errors.INCORRECT_FIELD_VALUE
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminCommonParamsViewModel @Inject constructor(
    private val commonParamsUseCase: CommonParamsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminCommonParamsState())
    var state = _state.asStateFlow()

    var commonParams by mutableStateOf(CommonParamsModel())
        private set
    var prizeFundError by mutableStateOf("")
        private set
    var exit by mutableStateOf(false)
        private set

    init {
        commonParamsUseCase.getCommonParams().onEach { commonParamsState ->
            _state.value = AdminCommonParamsState(commonParamsState)

            if (commonParamsState is UiState.Success) {
                commonParams = commonParamsState.data
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminCommonParamsEvent) {
        when (event) {
            is AdminCommonParamsEvent.OnPrizeFundChange -> {
                toLog("prizeFund = ${event.prizeFund}")
                prizeFundError = checkParam(event.prizeFund)
                toLog("prizeFundError = $prizeFundError")
                if (prizeFundError.isBlank()) {
                    commonParams = commonParams.copy(
                        prizeFund = event.prizeFund
                    )
                }
            }

            is AdminCommonParamsEvent.OnSave -> {
                commonParamsUseCase.saveCommonParams(commonParams).onEach { commonParamsState ->
                    _state.value = AdminCommonParamsState(commonParamsState)

                    if (commonParamsState is UiState.Success) exit = true
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun checkParam(param: String): String =
        if (param.toDoubleOrNull() == null) {
            INCORRECT_FIELD_VALUE
        } else ""

    data class AdminCommonParamsState(
        val result: UiState<CommonParamsModel> = UiState.Default
    )

    data class Errors(
        var prizeFundError: String = "",
        val groupPrizeFundError: String = "",
        val enabled: Boolean = prizeFundError.isNotBlank()
                || groupPrizeFundError.isNotBlank()
    )
}