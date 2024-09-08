package com.mu.tote2026.presentation.screen.admin.gambler.list

import androidx.lifecycle.ViewModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdminGamblerListViewModel @Inject constructor(

) : ViewModel() {
    private val _state: MutableStateFlow<AdminGamblerListState> = MutableStateFlow(AdminGamblerListState())
    val state = _state.asStateFlow()

    companion object {
        data class AdminGamblerListState(
            val result: UiState<List<GamblerModel>> = UiState.Default
        )
    }
}