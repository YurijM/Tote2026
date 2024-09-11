package com.mu.tote2026.domain.usecase.email_usecase

data class EmailUseCase(
    val getEmailList: GetEmailList,
    val getEmail: GetEmail,
    val saveEmail: SaveEmail,
    val deleteEmail: DeleteEmail
)
