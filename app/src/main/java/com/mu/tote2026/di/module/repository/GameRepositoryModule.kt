package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.GameRepositoryImpl
import com.mu.tote2026.domain.repository.GameRepository
import com.mu.tote2026.domain.usecase.gambler_usecase.GameUseCase
import com.mu.tote2026.domain.usecase.game_usecase.DeleteTeam
import com.mu.tote2026.domain.usecase.game_usecase.GetTeam
import com.mu.tote2026.domain.usecase.game_usecase.GetTeamList
import com.mu.tote2026.domain.usecase.game_usecase.SaveTeam
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameRepositoryModule {
    @Provides
    @Singleton
    fun provideGameRepository(
        firestore: FirebaseFirestore
    ) : GameRepository = GameRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideGameUseCase(gameRepository: GameRepository) = GameUseCase(
        getTeamList = GetTeamList(gameRepository),
        getTeam = GetTeam(gameRepository),
        saveTeam = SaveTeam(gameRepository),
        deleteTeam = DeleteTeam(gameRepository)
    )
}