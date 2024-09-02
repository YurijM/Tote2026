package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class GetGambler(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(email: String) =
        gamblerRepository.getGambler(email)
}