package com.mu.tote2026.domain.usecase.group_usecase

import com.mu.tote2026.domain.repository.GroupRepository

class DeleteGroup(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(id: String) = groupRepository.deleteGroup(id)
}