package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.EmailRepositoryImpl
import com.mu.tote2026.domain.repository.EmailRepository
import com.mu.tote2026.domain.usecase.email_usecase.DeleteEmail
import com.mu.tote2026.domain.usecase.email_usecase.EmailUseCase
import com.mu.tote2026.domain.usecase.email_usecase.GetEmail
import com.mu.tote2026.domain.usecase.email_usecase.GetEmailList
import com.mu.tote2026.domain.usecase.email_usecase.SaveEmail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmailRepositoryModule {
    @Provides
    @Singleton
    fun provideEmailRepository(
        firestore: FirebaseFirestore
    ) : EmailRepository = EmailRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideEmailUseCase(emailRepository: EmailRepository) = EmailUseCase(
        getEmailList = GetEmailList(emailRepository),
        getEmail = GetEmail(emailRepository),
        saveEmail = SaveEmail(emailRepository),
        deleteEmail = DeleteEmail(emailRepository)
    )
}