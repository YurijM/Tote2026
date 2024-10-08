package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeamList(): Flow<UiState<List<TeamModel>>>
    fun getTeam(id: String): Flow<UiState<TeamModel>>
    fun saveTeam(team: TeamModel): Flow<UiState<TeamModel>>
    fun deleteTeam(id: String): Flow<UiState<Boolean>>
}