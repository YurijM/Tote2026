package com.mu.tote2026.domain.usecase.auth_usecase

import com.mu.tote2026.domain.repository.AuthRepository

class GetCurrentGamblerId(
    private val authRepository: AuthRepository
) {
    operator fun invoke() =
        authRepository.getCurrentGamblerId()
}