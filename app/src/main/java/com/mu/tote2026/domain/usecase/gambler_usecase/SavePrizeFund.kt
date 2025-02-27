package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class SavePrizeFund(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(prizeFund: Int) =
        gamblerRepository.savePrizeFund(prizeFund)
}