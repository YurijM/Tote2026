package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.TeamRepositoryImpl
import com.mu.tote2026.domain.repository.TeamRepository
import com.mu.tote2026.domain.usecase.team_usecase.DeleteTeam
import com.mu.tote2026.domain.usecase.team_usecase.GetTeam
import com.mu.tote2026.domain.usecase.team_usecase.GetTeamList
import com.mu.tote2026.domain.usecase.team_usecase.SaveTeam
import com.mu.tote2026.domain.usecase.team_usecase.TeamUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamRepositoryModule {
    @Provides
    @Singleton
    fun provideTeamRepository(
        firestore: FirebaseFirestore
    ) : TeamRepository = TeamRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideTeamUseCase(teamRepository: TeamRepository) = TeamUseCase(
        getTeamList = GetTeamList(teamRepository),
        getTeam = GetTeam(teamRepository),
        saveTeam = SaveTeam(teamRepository),
        deleteTeam = DeleteTeam(teamRepository),
    )
}