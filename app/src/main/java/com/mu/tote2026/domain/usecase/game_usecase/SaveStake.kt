package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.repository.GameRepository

class SaveStake(
    private val gameRepository: GameRepository
) {
    operator fun invoke(oldStake: StakeModel, newStake: StakeModel) =
        gameRepository.saveStake(oldStake, newStake)
}