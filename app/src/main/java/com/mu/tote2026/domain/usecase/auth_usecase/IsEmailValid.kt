package com.mu.tote2026.domain.usecase.auth_usecase

import com.mu.tote2026.domain.repository.AuthRepository

class IsEmailValid(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String) =
        authRepository.isEmailValid(email)
}