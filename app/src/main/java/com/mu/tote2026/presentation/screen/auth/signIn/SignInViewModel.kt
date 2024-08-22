package com.mu.tote2026.presentation.screen.auth.signIn

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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    var enabledButton by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var errorEmail: String? = null
        private set
    var errorPassword: String? = null
        private set

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                email = event.email
                errorEmail = checkEmail(email = event.email)
                enabledButton = checkValues()
            }
            is SignInEvent.OnPasswordChange -> {
                password = event.password
                errorPassword = checkPassword(event.password, null)
                enabledButton = checkValues()
            }
            is SignInEvent.OnSignIn -> {
                viewModelScope.launch {
                    authUseCase.signIn(email, password).collect {
                        _state.value = SignInState(it)
                    }
                }
            }
        }
    }

    private fun checkValues(): Boolean {
        return (errorEmail != null && errorEmail!!.isBlank()) &&
                (errorPassword != null && errorPassword!!.isBlank())
    }

    companion object {
        data class SignInState(
            val result: UiState<Boolean> = UiState.Default,
        )
    }
}