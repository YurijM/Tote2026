package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    fun getEmailList(): Flow<UiState<List<EmailModel>>>
    fun getEmail(id: String): Flow<UiState<EmailModel>>
    fun saveEmail(email: EmailModel): Flow<UiState<EmailModel>>
    fun deleteEmail(id: String): Flow<UiState<Boolean>>
}