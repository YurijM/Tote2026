package com.mu.tote2026.presentation.screen.rating

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.GAMBLER
import com.mu.tote2026.domain.model.FinishModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.common_usecase.CommonUseCase
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
    gamblerUseCase: GamblerUseCase,
    commonUseCase: CommonUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RatingState())
    val state = _state.asStateFlow()

    var finish by mutableStateOf(FinishModel())
        private set

    var rateIsAbsent = false
        private set

    init {
        commonUseCase.getFinish().onEach { finishState ->
            if (finishState is UiState.Success)
                finish = finishState.data
        }.launchIn(viewModelScope)

        gamblerUseCase.getGamblerList().onEach { ratingState ->
            _state.value = RatingState(ratingState)

            if (ratingState is UiState.Success) {
                rateIsAbsent = (GAMBLER.rate == 0)
                _state.value = RatingState(
                    UiState.Success(
                        ratingState.data
                            .filter { it.rate > 0 }
                            .sortedWith(
                                compareByDescending<GamblerModel> { it.points }
                                    .thenByDescending { it.cashPrize }
                                    .thenBy { it.nickname }
                            )
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    data class RatingState(
        val result: UiState<List<GamblerModel>> = UiState.Default
    )
}