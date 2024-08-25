package com.mu.tote2026.presentation.screen.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.presentation.utils.checkEmail
import com.mu.tote2026.presentation.utils.checkPassword
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    var enabledButton by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordConfirm by mutableStateOf("")
        private set

    var errorEmail: String? = null
        private set
    var errorPassword: String? = null
        private set
    var errorPasswordConfirm: String? = null
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailChange -> {
                email = event.email
                errorEmail = checkEmail(email = event.email)
                enabledButton = checkValues()
            }
            is SignUpEvent.OnPasswordChange -> {
                password = event.password
                errorPassword = checkPassword(event.password, passwordConfirm)
                errorPasswordConfirm = checkPassword(passwordConfirm, event.password)
                enabledButton = checkValues()
            }
            is SignUpEvent.OnPasswordConfirmChange -> {
                passwordConfirm = event.passwordConfirm
                errorPasswordConfirm = checkPassword(event.passwordConfirm, password)
                errorPassword = checkPassword(password, event.passwordConfirm)
                enabledButton = checkValues()

            }
            is SignUpEvent.OnSignUp -> {}
        }
    }
    private fun checkValues(): Boolean = errorEmail.isNullOrBlank() && errorPassword.isNullOrBlank() && errorPasswordConfirm.isNullOrBlank()

    companion object {
        data class SignUpState(
            val result: UiState<Boolean> = UiState.Default,
        )
    }
}