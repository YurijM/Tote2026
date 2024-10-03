package com.mu.tote2026.presentation.screen.admin.group.list

import com.mu.tote2026.domain.model.GroupModel

sealed class AdminGroupListEvent {
    data class OnDelete(val group: GroupModel) : AdminGroupListEvent()
}