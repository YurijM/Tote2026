package com.mu.tote2026.di.module.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.AuthRepositoryImpl
import com.mu.tote2026.domain.repository.AuthRepository
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.domain.usecase.auth_usecase.GetCurrentGamblerId
import com.mu.tote2026.domain.usecase.auth_usecase.IsEmailValid
import com.mu.tote2026.domain.usecase.auth_usecase.SignIn
import com.mu.tote2026.domain.usecase.auth_usecase.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ) : AuthRepository = AuthRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(
        signUp = SignUp(authRepository),
        signIn = SignIn(authRepository),
        getCurrentGamblerId = GetCurrentGamblerId(authRepository),
        isEmailValid = IsEmailValid(authRepository)
    )
}