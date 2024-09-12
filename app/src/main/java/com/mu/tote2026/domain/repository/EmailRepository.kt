package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    fun getEmailList(): Flow<UiState<List<EmailModel>>>
    fun getEmail(email: String): Flow<UiState<EmailModel>>
    fun saveEmail(email: String): Flow<UiState<EmailModel>>
    fun deleteEmail(email: String): Flow<UiState<Boolean>>
}