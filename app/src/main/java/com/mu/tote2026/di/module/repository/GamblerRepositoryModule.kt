package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.GamblerRepositoryImpl
import com.mu.tote2026.domain.repository.GamblerRepository
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.domain.usecase.gambler_usecase.GetGambler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GamblerRepositoryModule {
    @Provides
    @Singleton
    fun provideGamblerRepository(
        firestore: FirebaseFirestore
    ) : GamblerRepository = GamblerRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideGamblerUseCase(gamblerRepository: GamblerRepository) = GamblerUseCase(
        getGambler = GetGambler(gamblerRepository),
    )
}