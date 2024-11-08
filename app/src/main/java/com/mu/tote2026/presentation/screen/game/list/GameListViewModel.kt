package com.mu.tote2026.presentation.screen.game.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GroupTeamResultModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<GroupTeamResultState> = MutableStateFlow(GroupTeamResultState())
    val state = _state.asStateFlow()

    init {
        gameUseCase.getGroupTeamResult().onEach { groupTeamResultState ->
            _state.value = GroupTeamResultState(groupTeamResultState)

            if (groupTeamResultState is UiState.Success) {
                _state.value = GroupTeamResultState(
                    UiState.Success(groupTeamResultState.data)
                )
            }
        }.launchIn(viewModelScope)
    }

    //companion object {
    data class GroupTeamResultState(
        val result: UiState<Map<String, List<GroupTeamResultModel>>> = UiState.Default
    )
    //}
}