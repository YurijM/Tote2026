package com.mu.tote2026.domain.usecase.common_usecase

import com.mu.tote2026.domain.model.FinishModel
import com.mu.tote2026.domain.repository.CommonRepository

class SaveFinish(
    private val commonRepository: CommonRepository
) {
    operator fun invoke(finish: FinishModel) = commonRepository.saveFinish(finish)
}