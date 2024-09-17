package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.repository.GamblerRepository

class SaveGambler(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(gambler: GamblerModel) =
        //gamblerRepository.saveGambler(id, gambler)
        gamblerRepository.saveGambler(gambler)
}