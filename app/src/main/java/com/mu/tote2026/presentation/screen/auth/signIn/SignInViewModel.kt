package com.mu.tote2026.presentation.screen.auth.signIn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.presentation.utils.checkEmail
import com.mu.tote2026.presentation.utils.toLog
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

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var errorEmail: String? = null
    var errorPassword: String? = null

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                email = event.email
                errorEmail = checkEmail(email = event.email)
            }
            is SignInEvent.OnPasswordChange -> {
                password = event.password
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

    companion object {
        data class SignInState(
            val result: UiState<Boolean> = UiState.Default,
        )
    }
}