package com.mu.tote2026.presentation.screen.admin.email.list

import androidx.lifecycle.ViewModel
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdminEmailListViewModel @Inject constructor(

) : ViewModel() {
    private val _state: MutableStateFlow<AdminEmailListState> = MutableStateFlow(AdminEmailListState())
    val state: StateFlow<AdminEmailListState> = _state.asStateFlow()

    companion object {
        data class AdminEmailListState(
            val result: UiState<List<EmailModel>> = UiState.Default
        )
    }
}
