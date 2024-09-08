package com.mu.tote2026.presentation.screen.admin.game

import androidx.lifecycle.ViewModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdminGameListViewModel @Inject constructor(

) : ViewModel() {
    companion object {
        private val _state = MutableStateFlow(AdminGameListState())
        val state = _state.asStateFlow()

        data class AdminGameListState(
            val result: UiState<List<GameModel>> = UiState.Default
        )
    }
}