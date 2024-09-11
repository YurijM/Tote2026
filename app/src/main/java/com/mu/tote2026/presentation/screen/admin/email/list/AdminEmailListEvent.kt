package com.mu.tote2026.presentation.screen.admin.email.list

import com.mu.tote2026.domain.model.EmailModel

sealed class AdminEmailListEvent {
    data class OnDelete(val email: EmailModel) : AdminEmailListEvent()
}