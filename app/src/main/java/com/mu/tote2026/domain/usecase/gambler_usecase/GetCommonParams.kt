package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.repository.GamblerRepository

class GetCommonParams(
    private val gamblerRepository: GamblerRepository
) {
    operator fun invoke() = gamblerRepository.getCommonParams()
}