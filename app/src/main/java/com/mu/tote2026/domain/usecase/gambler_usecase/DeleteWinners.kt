package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class DeleteWinners(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke() =
        gamblerRepository.deleteWinners()
}