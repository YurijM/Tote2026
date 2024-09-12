package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class SaveGamblerPhoto(
    private val gamblerRepository: GamblerRepository
) {
    //operator fun invoke(id: String, uri: Uri) =
    operator fun invoke(id: String, uri: ByteArray) =
        gamblerRepository.saveGamblerPhoto(id, uri)
}