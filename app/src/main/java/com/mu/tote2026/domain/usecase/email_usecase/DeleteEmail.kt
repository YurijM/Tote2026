package com.mu.tote2026.domain.usecase.email_usecase

import com.mu.tote2026.domain.repository.EmailRepository

class DeleteEmail(
    private val emailRepository: EmailRepository
) {
    operator fun invoke(id: String) = emailRepository.deleteEmail(id)
}