package com.mu.tote2026.domain.usecase.gambler_usecase

import android.net.Uri
import com.mu.tote2026.domain.repository.GamblerRepository

class SaveGamblerPhoto(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke(id: String, uri: Uri) =
        gamblerRepository.saveGamblerPhoto(id, uri)
}