package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.CommonRepositoryImpl
import com.mu.tote2026.domain.repository.CommonRepository
import com.mu.tote2026.domain.usecase.common_usecase.CommonUseCase
import com.mu.tote2026.domain.usecase.common_usecase.GetPrizeFund
import com.mu.tote2026.domain.usecase.common_usecase.SavePrizeFund
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonRepositoryModule {
    @Provides
    @Singleton
    fun provideCommonRepository(
        firestore: FirebaseFirestore
    ) : CommonRepository = CommonRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideCommonUseCase(commonRepository: CommonRepository) = CommonUseCase(
        getPrizeFund = GetPrizeFund(commonRepository),
        savePrizeFund = SavePrizeFund(commonRepository),
    )
}