package com.mu.tote2026.domain.usecase.group_usecase

import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.domain.repository.GroupRepository

class SaveGroup(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(group: GroupModel) = groupRepository.saveGroup(group)
}