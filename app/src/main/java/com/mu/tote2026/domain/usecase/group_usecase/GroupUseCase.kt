package com.mu.tote2026.domain.usecase.group_usecase

data class GroupUseCase(
    val getGroupList: GetGroupList,
    val getGroup: GetGroup,
    val saveGroup: SaveGroup,
    val deleteGroup: DeleteGroup
)
