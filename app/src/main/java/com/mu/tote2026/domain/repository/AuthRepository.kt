package com.mu.tote2026.domain.repository

import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(
        email: String,
        password: String
    ): Flow<UiState<Boolean>>

    fun signIn(
        email: String,
        password: String
    ): Flow<UiState<Boolean>>

    fun getCurrentGamblerId(): String

    fun isEmailValid(email: String): Boolean
}