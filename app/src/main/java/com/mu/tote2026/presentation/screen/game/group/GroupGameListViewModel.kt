package com.mu.tote2026.presentation.screen.game.group

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupGameListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GroupGamesState())
    val state = _state.asStateFlow()

    init {
        val args = savedStateHandle.toRoute<GroupGamesDestination>()

        gameUseCase.getGameList().onEach { groupGamesState ->
            _state.value = GroupGamesState(groupGamesState)

            if (groupGamesState is UiState.Success) {
                val games = groupGamesState.data.filter { game -> game.group == args.group }.sortedBy { it.id.toInt() }
                _state.value = GroupGamesState(
                    UiState.Success(games)
                )
            }
        }.launchIn(viewModelScope)
    }

    data class GroupGamesState(
        val result: UiState<List<GameModel>> = UiState.Default
    )
}