package com.mu.tote2026.presentation.screen.auth.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.data.repository.GAMBLER
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.utils.checkEmail
import com.mu.tote2026.presentation.utils.checkPassword
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    var enabledButton by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var errorEmail: String = ""
        private set
    var errorPassword: String = ""
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
                authUseCase.signIn(email, password).onEach { signState ->
                    _state.value = SignInState(signState)

                    if (signState is UiState.Success) {
                        gamblerUseCase.getGambler(CURRENT_ID).onEach { currentGamblerState ->
                            when (currentGamblerState) {
                                is UiState.Loading -> _state.value = SignInState(currentGamblerState)
                                is UiState.Success -> {
                                    GAMBLER = currentGamblerState.data
                                    _state.value = SignInState(UiState.Success(true))
                                }

                                is UiState.Error -> _state.value = SignInState(UiState.Error(currentGamblerState.error))
                                else -> {}
                            }
                        }.launchIn(viewModelScope)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun checkValues(): Boolean = (errorEmail.isBlank() && errorPassword.isBlank())

    data class SignInState(
        val result: UiState<Boolean> = UiState.Default,
    )
}