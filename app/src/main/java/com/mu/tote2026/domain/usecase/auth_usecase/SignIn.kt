package com.mu.tote2026.domain.usecase.auth_usecase

import com.mu.tote2026.domain.repository.AuthRepository

class SignIn(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        authRepository.signIn(email, password)
}