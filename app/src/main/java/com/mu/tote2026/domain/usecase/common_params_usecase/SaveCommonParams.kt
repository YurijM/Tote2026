package com.mu.tote2026.domain.usecase.common_params_usecase

import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.repository.CommonParamsRepository

class SaveCommonParams(
    private val commonParamsRepository: CommonParamsRepository
) {
    operator fun invoke(commonParams: CommonParamsModel) = commonParamsRepository.saveCommonParams(commonParams)
}