package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.model.WinnerModel
import com.mu.tote2026.domain.repository.GamblerRepository

class SaveWinner(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(winner: WinnerModel) =
        gamblerRepository.saveWinner(winner)
}