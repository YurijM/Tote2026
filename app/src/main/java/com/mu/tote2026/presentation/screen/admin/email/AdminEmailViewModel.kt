package com.mu.tote2026.presentation.screen.admin.email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.usecase.email_usecase.EmailUseCase
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.NEW_EMAIL
import com.mu.tote2026.presentation.utils.checkEmail
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminEmailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val emailUseCase: EmailUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminEmailState> = MutableStateFlow(AdminEmailState())
    val state = _state.asStateFlow()

    var email by mutableStateOf(EmailModel(id = NEW_EMAIL))
        private set
    var emailError by mutableStateOf("")

    var exit by mutableStateOf(false)

    init {
        val id = savedStateHandle.get<String>(KEY_ID)
        toLog("id: $id")

        if (!id.isNullOrBlank() && id != NEW_EMAIL) {
            emailUseCase.getEmail(id).onEach { emailState ->
                _state.value = AdminEmailState(emailState)

                if (emailState is UiState.Success) {
                    email = emailState.data
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: AdminEmailEvent) {
        when (event) {
            is AdminEmailEvent.OnEmailChange -> {
                emailError = checkEmail(event.email)
                email = email.copy(
                    email = event.email
                )
            }
            is AdminEmailEvent.OnSave -> {
                emailUseCase.saveEmail(email).onEach { emailState ->
                    _state.value = AdminEmailState(emailState)

                    if (emailState is UiState.Success) exit = true
                }.launchIn(viewModelScope)
            }
        }
    }

    companion object {
        data class AdminEmailState(
            val result: UiState<EmailModel> = UiState.Default
        )
    }
}