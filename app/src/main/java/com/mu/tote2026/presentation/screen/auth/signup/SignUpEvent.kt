package com.mu.tote2026.presentation.screen.auth.signup

sealed class SignUpEvent {
    data class OnEmailChange(val email: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    data class OnPasswordConfirmChange(val passwordConfirm: String) : SignUpEvent()
    data object OnSignUp : SignUpEvent()
}
