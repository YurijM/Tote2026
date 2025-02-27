package com.mu.tote2026.domain.usecase.common_usecase

import com.mu.tote2026.domain.repository.CommonRepository

class GetFinish(
    private val commonRepository: CommonRepository
) {
    operator fun invoke() = commonRepository.getFinish()
}