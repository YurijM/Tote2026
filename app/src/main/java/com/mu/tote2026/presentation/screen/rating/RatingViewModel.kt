package com.mu.tote2026.presentation.screen.rating

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
class RatingViewModel @Inject constructor(
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RatingState())
    val state = _state.asStateFlow()

    var gamblers = mutableListOf<GamblerModel>()

    init {
        gamblerUseCase.getGamblerList().onEach { ratingState ->
            _state.value = RatingState(ratingState)

            if (ratingState is UiState.Success)
                gamblers = ratingState.data
                    .filter { it.rate > 0 }
                    .sortedBy { it.nickname }
                    .toMutableList()
        }.launchIn(viewModelScope)
    }

    companion object {
        data class RatingState(
            val result: UiState<List<GamblerModel>> = UiState.Default
        )
    }
}