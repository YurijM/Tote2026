package com.mu.tote2026.presentation.screen.admin.gambler.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminGamblerListViewModel @Inject constructor(
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminGamblerListState> = MutableStateFlow(AdminGamblerListState())
    val state = _state.asStateFlow()

    var gamblerList = mutableListOf<GamblerModel>()
        private set
    var prizeFund = 0

    init {
        gamblerUseCase.getGamblerList().onEach { gamblerListState ->
            _state.value = AdminGamblerListState(gamblerListState)

            if (gamblerListState is UiState.Success) {
                gamblerList = gamblerListState.data.toMutableList()
                gamblerList.sortBy { it.nickname }
                prizeFund = gamblerList.sumOf { it.rate }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        data class AdminGamblerListState(
            val result: UiState<List<GamblerModel>> = UiState.Default
        )
    }
}