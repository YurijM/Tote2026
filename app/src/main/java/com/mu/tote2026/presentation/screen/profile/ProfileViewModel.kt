package com.mu.tote2026.presentation.screen.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.Errors.ERROR_PROFILE_IS_EMPTY
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.presentation.utils.checkProfile
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

    var gambler by mutableStateOf(GamblerModel())
        private set
    var exit by mutableStateOf(false)
        private set
    private var currentGambler: GamblerModel = GamblerModel()

    private var photoUri by mutableStateOf<Uri?>(null)

    var enabledSaveButton by mutableStateOf(false)
        private set

    var nicknameError: String? = null
        private set
    var genderError: String? = null
        private set
    private var photoUrlError: String? = null

    init {
        gamblerUseCase.getGambler(CURRENT_ID).onEach { gamblerState ->
            _state.value = GamblerState(gamblerState)

            if (gamblerState is UiState.Success) {
                gambler = gamblerState.data
                currentGambler = gambler
                enabledSaveButton = checkValues()
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnNicknameChange -> {
                gambler = gambler.copy(nickname = event.nickname)
                enabledSaveButton = checkValues()
            }

            is ProfileEvent.OnGenderChange -> {
                gambler = gambler.copy(gender = event.gender)
                enabledSaveButton = checkValues()
            }

            is ProfileEvent.OnPhotoChange -> {
                gambler = gambler.copy(photoUrl = event.uri.toString())
                photoUri = event.uri
                enabledSaveButton = checkValues()
            }

            is ProfileEvent.OnSave -> {
                if (photoUri != null) {
                    //_state.value = GamblerState(UiState.Loading)

                    gamblerUseCase.saveGamblerPhoto(CURRENT_ID, photoUri!!).onEach { photoState ->
                        if (photoState is UiState.Success) {
                            gambler = gambler.copy(photoUrl = photoState.data)
                            exit = true
                            gamblerUseCase.saveGambler(gambler).onEach { gamblerState ->
                                _state.value = GamblerState(gamblerState)
                            }.launchIn(viewModelScope)
                        } else if (photoState is UiState.Error) {
                            _state.value = GamblerState(UiState.Error(photoState.error))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    gamblerUseCase.saveGambler(gambler).onEach { gamblerState ->
                        _state.value = GamblerState(gamblerState)
                        if (gamblerState is UiState.Success) {
                            exit = true
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is ProfileEvent.OnCancel -> {
                if (currentGambler.checkProfile()) {
                    exit = true
                    currentGambler = currentGambler.copy(nickname = "")
                    _state.value = GamblerState(UiState.Success(currentGambler))
                } else
                    _state.value = GamblerState(UiState.Error(ERROR_PROFILE_IS_EMPTY))
            }
        }
    }

    private fun checkValues(): Boolean {
        nicknameError = checkIsFieldEmpty(gambler.nickname)
        photoUrlError = checkIsFieldEmpty(gambler.photoUrl)
        genderError = checkIsFieldEmpty(gambler.gender)

        return nicknameError.isNullOrBlank() &&
                photoUrlError.isNullOrBlank() &&
                genderError.isNullOrBlank()
    }

    data class GamblerState(
        val result: UiState<GamblerModel> = UiState.Default,
    )
}