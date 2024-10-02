package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroupList(): Flow<UiState<List<GroupModel>>>
    fun getGroup(id: String): Flow<UiState<GroupModel>>
    fun saveGroup(group: GroupModel): Flow<UiState<GroupModel>>
    fun deleteGroup(id: String): Flow<UiState<Boolean>>
}