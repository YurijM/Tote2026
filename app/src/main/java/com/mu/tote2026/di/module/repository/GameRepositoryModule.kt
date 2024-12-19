package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.GameRepositoryImpl
import com.mu.tote2026.domain.repository.GameRepository
import com.mu.tote2026.domain.usecase.game_usecase.DeleteGame
import com.mu.tote2026.domain.usecase.game_usecase.DeleteStakes
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.domain.usecase.game_usecase.GetGamblerGameStake
import com.mu.tote2026.domain.usecase.game_usecase.GetGamblerStakes
import com.mu.tote2026.domain.usecase.game_usecase.GetGame
import com.mu.tote2026.domain.usecase.game_usecase.GetGameList
import com.mu.tote2026.domain.usecase.game_usecase.GetGameSum
import com.mu.tote2026.domain.usecase.game_usecase.SaveGame
import com.mu.tote2026.domain.usecase.game_usecase.SaveStake
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
        getGameList = GetGameList(gameRepository),
        getGame = GetGame(gameRepository),
        saveGame = SaveGame(gameRepository),
        deleteGame = DeleteGame(gameRepository),

        getGamblerStakes = GetGamblerStakes(gameRepository),
        getGamblerGameStake = GetGamblerGameStake(gameRepository),
        deleteStakes = DeleteStakes(gameRepository),
        saveStake = SaveStake(gameRepository),

        getGameSum = GetGameSum(gameRepository)
    )
}