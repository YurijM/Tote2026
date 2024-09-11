package com.mu.tote2026.domain.usecase.email_usecase

import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.repository.EmailRepository

class DeleteEmail(
    private val emailRepository: EmailRepository
) {
    operator fun invoke(email: EmailModel) = emailRepository.deleteEmail(email)
}