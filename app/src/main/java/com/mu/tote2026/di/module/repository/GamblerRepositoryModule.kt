package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mu.tote2026.data.repository.GamblerRepositoryImpl
import com.mu.tote2026.domain.repository.GamblerRepository
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.domain.usecase.gambler_usecase.GetGambler
import com.mu.tote2026.domain.usecase.gambler_usecase.GetGamblerList
import com.mu.tote2026.domain.usecase.gambler_usecase.SaveGambler
import com.mu.tote2026.domain.usecase.gambler_usecase.SaveGamblerPhoto
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
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ) : GamblerRepository = GamblerRepositoryImpl(firestore, storage)

    @Provides
    @Singleton
    fun provideGamblerUseCase(gamblerRepository: GamblerRepository) = GamblerUseCase(
        getGamblerList = GetGamblerList(gamblerRepository),
        getGambler = GetGambler(gamblerRepository),
        saveGambler = SaveGambler(gamblerRepository),
        saveGamblerPhoto = SaveGamblerPhoto(gamblerRepository)
    )
}