package com.mu.tote2026.presentation.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<GamblerState> = MutableStateFlow(GamblerState())
    val state: StateFlow<GamblerState> = _state.asStateFlow()

    var enabledButton by mutableStateOf(false)
        private set

    var nickname by mutableStateOf("")
        private set
    var photoUrl by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set

    var errorNickname: String? = null
        private set
    var errorPhotoUrl: String? = null
        private set
    var errorGender: String? = null
        private set

    init {
        gamblerUseCase.getGambler(CURRENT_ID).onEach { stateGambler ->
            _state.value = GamblerState(stateGambler)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnNicknameChange -> {}
            is ProfileEvent.OnPhotoUrlChange -> {}
            is ProfileEvent.OnGenderChange -> {}
            is ProfileEvent.OnSave -> {}
        }
    }

    companion object {
        data class GamblerState(
            val result: UiState<GamblerModel> = UiState.Default,
        )
    }
}