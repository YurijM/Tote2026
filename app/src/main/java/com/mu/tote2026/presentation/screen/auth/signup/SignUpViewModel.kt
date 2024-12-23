package com.mu.tote2026.presentation.screen.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.presentation.utils.checkEmail
import com.mu.tote2026.presentation.utils.checkPassword
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    var errorEmail: String = ""
        private set
    var errorPassword: String = ""
        private set
    var errorPasswordConfirm: String = ""
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

            is SignUpEvent.OnSignUp -> {
                viewModelScope.launch {
                    authUseCase.isEmailValid(email).collect { emailState ->
                        _state.value = SignUpState(emailState)

                        if (emailState is UiState.Success && emailState.data) {
                            authUseCase.signUp(email, password).collect { signState ->
                                _state.value = SignUpState(signState)
                            }
                        }
                    }
                }
            }
        }
    }

    /*private fun checkValues(): Boolean = (errorEmail != null && errorEmail!!.isBlank()) &&
            (errorPassword != null && errorPassword!!.isBlank()) &&
            (errorPasswordConfirm != null && errorPasswordConfirm!!.isBlank())*/
    private fun checkValues(): Boolean = (errorEmail.isBlank() && errorPassword.isBlank() && errorPasswordConfirm.isBlank())

    data class SignUpState(
        val result: UiState<Boolean> = UiState.Default,
    )
}