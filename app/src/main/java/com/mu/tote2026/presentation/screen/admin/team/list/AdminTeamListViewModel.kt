package com.mu.tote2026.presentation.screen.admin.team.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminTeamListViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminTeamListState())
    val state = _state.asStateFlow()

    var teamList = mutableListOf<TeamModel>()

    init {
        gameUseCase.getTeamList().onEach { teamListState ->
            _state.value = AdminTeamListState(teamListState)

            if (teamListState is UiState.Success)
                teamList = teamListState.data.toMutableList()
        }.launchIn(viewModelScope)
    }

    companion object {
        data class AdminTeamListState(
            val result: UiState<List<TeamModel>> = UiState.Default
        )
    }

}