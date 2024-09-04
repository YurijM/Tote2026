package com.mu.tote2026.presentation.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
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
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<GamblerState> = MutableStateFlow(GamblerState())
    val state: StateFlow<GamblerState> = _state.asStateFlow()

    var gambler by mutableStateOf(GamblerModel())
        private set

    var enabledButton by mutableStateOf(false)
        private set

    var nicknameError: String? = null
        private set
    var photoUrlError: String? = null
        private set
    var genderError: String? = null
        private set

    init {
        gamblerUseCase.getGambler(CURRENT_ID).onEach { gamblerState ->
            val result = GamblerState(gamblerState).result

            if (result is UiState.Success) {
                gambler = result.data
                enabledButton = checkValues()
            }

            _state.value = GamblerState(gamblerState)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnNicknameChange -> {
                gambler = gambler.copy(nickname = event.nickname)
                enabledButton = checkValues()
            }
            is ProfileEvent.OnGenderChange -> {
                gambler = gambler.copy(gender = event.gender)
                enabledButton = checkValues()
            }
            is ProfileEvent.OnPhotoUrlChange -> {}
            is ProfileEvent.OnSave -> {}
        }
    }

    private fun checkValues(): Boolean {
            nicknameError = checkIsFieldEmpty(gambler.nickname)
            photoUrlError = checkIsFieldEmpty(gambler.photoUrl)
            genderError = checkIsFieldEmpty(gambler.gender)

        return nicknameError.isNullOrBlank() &&
                //photoUrlError.isNullOrBlank() &&
                genderError.isNullOrBlank()
    }

    companion object {
        data class GamblerState(
            val result: UiState<GamblerModel> = UiState.Default,
        )
    }
}