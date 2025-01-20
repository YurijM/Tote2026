package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.CommonParamsRepositoryImpl
import com.mu.tote2026.domain.repository.CommonParamsRepository
import com.mu.tote2026.domain.usecase.common_params_usecase.CommonParamsUseCase
import com.mu.tote2026.domain.usecase.common_params_usecase.GetCommonParams
import com.mu.tote2026.domain.usecase.common_params_usecase.SaveCommonParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonParamsRepositoryModule {
    @Provides
    @Singleton
    fun provideCommonParamsRepository(
        firestore: FirebaseFirestore
    ) : CommonParamsRepository = CommonParamsRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideCommonParamsUseCase(commonParamsRepository: CommonParamsRepository) = CommonParamsUseCase(
        getCommonParams = GetCommonParams(commonParamsRepository),
        saveCommonParams = SaveCommonParams(commonParamsRepository),
    )
}