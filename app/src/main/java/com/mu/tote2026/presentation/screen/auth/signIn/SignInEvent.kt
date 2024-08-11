package com.mu.tote2026.presentation.screen.auth.signIn

sealed class SignInEvent {
    data class OnEmailChange(val email: String) : SignInEvent()
    data class OnPasswordChange(val password: String) : SignInEvent()
    data object OnSignIn : SignInEvent()
}
