package com.mu.tote2026.presentation.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.Errors.ERROR_PROFILE_IS_EMPTY
import com.mu.tote2026.presentation.utils.checkProfile
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    gamblerUseCase: GamblerUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<GamblerState> = MutableStateFlow(GamblerState())
    val state: StateFlow<GamblerState> = _state.asStateFlow()

    var gambler by mutableStateOf(GamblerModel())
        private set

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    init {
        gamblerUseCase.getGambler(CURRENT_ID).onEach { gamblerState ->
            toLog("MainViewModel CURRENT_ID: $CURRENT_ID")
            if (CURRENT_ID.isNotBlank()) {
                _state.value = GamblerState(gamblerState)

                val result = GamblerState(gamblerState).result

                if (result is UiState.Success) {
                    gambler = result.data
                    if (!gambler.checkProfile())
                        _state.value = GamblerState(UiState.Error(ERROR_PROFILE_IS_EMPTY))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun signOut() {
        CURRENT_ID = ""
        firebaseAuth.signOut()
    }

    companion object {
        data class GamblerState(
            val result: UiState<GamblerModel> = UiState.Default,
        )
    }
}