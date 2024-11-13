package com.mu.tote2026.presentation.screen.game.group

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.mu.tote2026.presentation.navigation.Destinations
import com.mu.tote2026.presentation.utils.toLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupGameListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    init {
        val args = savedStateHandle.toRoute<Destinations.GroupGamesDestination>()
        toLog("args: $args")
    }
}