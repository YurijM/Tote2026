package com.mu.tote2026.presentation.screen.admin.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.usecase.common_params_usecase.CommonParamsUseCase
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.Errors.INCORRECT_FIELD_VALUE
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminCommonParamsViewModel @Inject constructor(
    private val commonParamsUseCase: CommonParamsUseCase,
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminCommonParamsState())
    var state = _state.asStateFlow()

    var commonParams by mutableStateOf(CommonParamsModel())
        private set
    var prizeFund by mutableIntStateOf(0)
        private set
    var errors by mutableStateOf(Errors())
        private set
    var enabled by mutableStateOf(false)
        private set
    var exit by mutableStateOf(false)
        private set

    init {
        gamblerUseCase.getGamblerList().onEach { gamblersState ->
            if (gamblersState is UiState.Success)
                prizeFund = gamblersState.data.sumOf { it.rate }
        }.launchIn(viewModelScope)

        commonParamsUseCase.getCommonParams().onEach { commonParamsState ->
            _state.value = AdminCommonParamsState(commonParamsState)

            if (commonParamsState is UiState.Success) {
                commonParams = commonParamsState.data
                enabled = checkEnabled()
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminCommonParamsEvent) {
        when (event) {
            is AdminCommonParamsEvent.OnPrizeFundChange -> {
                errors = errors.copy(prizeFundError = checkParam(event.money))
                enabled = checkEnabled()
                commonParams = commonParams.copy(
                    prizeFund = event.money
                )
            }

            is AdminCommonParamsEvent.OnGroupPrizeFundChange -> {
                errors = errors.copy(groupPrizeFundError = checkParam(event.money))
                enabled = checkEnabled()
                commonParams = commonParams.copy(
                    groupPrizeFund = event.money
                )
            }

            is AdminCommonParamsEvent.OnPlayoffPrizeFundChange -> {
                errors = errors.copy(playoffPrizeFundError = checkParam(event.money))
                enabled = checkEnabled()
                commonParams = commonParams.copy(
                    playoffPrizeFund = event.money
                )
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

    private fun checkEnabled(): Boolean = errors.prizeFundError.isBlank()
            && errors.groupPrizeFundError.isBlank()
            && errors.playoffPrizeFundError.isBlank()

    data class AdminCommonParamsState(
        val result: UiState<CommonParamsModel> = UiState.Default
    )

    data class Errors(
        var prizeFundError: String = "",
        val groupPrizeFundError: String = "",
        val playoffPrizeFundError: String = "",
    )
}