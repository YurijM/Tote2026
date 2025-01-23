package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class SaveCommonParams(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(prizeFund: Int) =
        gamblerRepository.saveCommonParams(prizeFund)
}