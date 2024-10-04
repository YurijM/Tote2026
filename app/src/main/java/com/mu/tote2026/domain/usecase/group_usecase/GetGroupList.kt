package com.mu.tote2026.domain.usecase.group_usecase

import com.mu.tote2026.domain.repository.GroupRepository

class GetGroupList(
    private val groupRepository: GroupRepository
) {
    operator fun invoke() = groupRepository.getGroupList()
}