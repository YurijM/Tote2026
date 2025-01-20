package com.mu.tote2026.domain.usecase.common_params_usecase

import com.mu.tote2026.domain.repository.CommonParamsRepository

class GetCommonParams(
    private val commonParamsRepository: CommonParamsRepository
) {
    operator fun invoke() = commonParamsRepository.getCommonParams()
}