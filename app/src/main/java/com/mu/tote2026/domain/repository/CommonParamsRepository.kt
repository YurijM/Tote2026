package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface CommonParamsRepository {
    fun getCommonParams(): Flow<UiState<CommonParamsModel>>
    fun saveCommonParams(commonParams: CommonParamsModel): Flow<UiState<CommonParamsModel>>
}