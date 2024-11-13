package com.mu.tote2026.presentation.screen.admin.gambler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.Errors.FIELD_CAN_NOT_BE_EMPTY
import com.mu.tote2026.presentation.utils.Errors.FIELD_CAN_NOT_NEGATIVE
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminGamblerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminGamblerState> = MutableStateFlow(AdminGamblerState())
    val state = _state.asStateFlow()

    var gambler by mutableStateOf(GamblerModel())
        private set
    var rateError by mutableStateOf("")
        private set
    var exit by mutableStateOf(false)
        private set

    init {
        val id = savedStateHandle.get<String>(KEY_ID)
        if (!id.isNullOrBlank()) {
            gamblerUseCase.getGambler(id).onEach { gamblerState ->
                _state.value = AdminGamblerState(gamblerState)
                if (gamblerState is UiState.Success) {
                    gambler = gamblerState.data
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: AdminGamblerEvent) {
        when (event) {
            is AdminGamblerEvent.OnRateChange -> {
                rateError = checkRate(event.rate)
                gambler = if (rateError.isBlank()) {
                    gambler.copy(rate = event.rate.toInt())
                } else {
                    gambler.copy(rate = 0)
                }
            }

            is AdminGamblerEvent.OnIsAdminChange -> {
                gambler = gambler.copy(
                    isAdmin = event.isAdmin
                )
            }

            is AdminGamblerEvent.OnSave -> {
                gamblerUseCase.saveGambler(gambler).onEach { gamblerSaveState ->
                    _state.value = AdminGamblerState(gamblerSaveState)
                    exit = true
                    /*if (gamblerSaveState is UiState.Success) {
                        toLog("2")
                    }*/
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun checkRate(rate: String): String {
        return when {
            rate.isBlank() -> FIELD_CAN_NOT_BE_EMPTY
            rate.toInt() < 0 -> FIELD_CAN_NOT_NEGATIVE
            else -> ""
        }
    }

    data class AdminGamblerState(
        val result: UiState<GamblerModel> = UiState.Default
    )
}