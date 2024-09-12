package com.mu.tote2026.domain.usecase.email_usecase

import com.mu.tote2026.domain.repository.EmailRepository

class SaveEmail(
    private val emailRepository: EmailRepository
) {
    operator fun invoke(email: String) = emailRepository.saveEmail(email)
}