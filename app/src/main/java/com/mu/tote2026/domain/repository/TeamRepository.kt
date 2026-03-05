package com.mu.tote2026.domain.repository

import android.net.Uri
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeamList(): Flow<UiState<List<TeamModel>>>
    fun getTeam(id: String): Flow<UiState<TeamModel>>
    fun saveTeam(team: TeamModel): Flow<UiState<TeamModel>>
    fun saveTeamFlag(id: String, uri: Uri): Flow<UiState<String>>
    fun deleteTeam(id: String): Flow<UiState<Boolean>>
    fun deleteTeamFlag(id: String): Flow<UiState<Boolean>>
}