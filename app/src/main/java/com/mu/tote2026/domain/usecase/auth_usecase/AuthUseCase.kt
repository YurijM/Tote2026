package com.mu.tote2026.domain.usecase.auth_usecase

data class AuthUseCase(
    val signUp: SignUp,
    val signIn: SignIn,
    val getCurrentGamblerID: GetCurrentGamblerID
)
