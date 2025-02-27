package com.mu.tote2026.domain.usecase.common_usecase

import com.mu.tote2026.domain.repository.CommonRepository

class SavePrizeFund(
    private val commonRepository: CommonRepository
) {
    operator fun invoke(prizeFund: Int) = commonRepository.savePrizeFund(prizeFund)
}