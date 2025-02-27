package com.mu.tote2026.presentation.screen.admin.finish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2024.presentation.screen.admin.finish.AdminFinishEvent
import com.mu.tote2026.domain.model.FinishModel
import com.mu.tote2026.domain.usecase.common_usecase.CommonUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminFinishViewModel @Inject constructor(
    private val commonUseCase: CommonUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminFinishState> = MutableStateFlow(AdminFinishState())
    val state: StateFlow<AdminFinishState> = _state.asStateFlow()

    var finish by mutableStateOf(FinishModel())
        private set

    init {
        commonUseCase.getFinish().onEach { finishState ->
            _state.value = AdminFinishState(finishState)
            if (finishState is UiState.Success) {
                finish = finishState.data
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminFinishEvent) {
        when (event) {
            is AdminFinishEvent.OnFinishedChange -> {
                finish = finish.copy(
                    finished = event.finished
                )
            }

            is AdminFinishEvent.OnChampionChange -> {
                finish = finish.copy(
                    champion = event.champion
                )
            }

            is AdminFinishEvent.OnCancel -> {
                _state.value = AdminFinishState(UiState.Success(finish))
                //isExit = true
            }

            is AdminFinishEvent.OnSave -> {
                commonUseCase.saveFinish(finish).onEach { finishState ->
                    //isExit = true
                    _state.value = AdminFinishState(finishState)
                }.launchIn(viewModelScope)
            }
        }
    }

    data class AdminFinishState(
        val result: UiState<FinishModel> = UiState.Default,
    )
}
