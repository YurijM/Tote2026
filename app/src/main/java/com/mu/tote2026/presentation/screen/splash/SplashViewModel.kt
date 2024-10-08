package com.mu.tote2026.presentation.screen.splash

import androidx.lifecycle.ViewModel
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    authUseCase: AuthUseCase
) : ViewModel() {
    var isAuth = false

    init {
        CURRENT_ID = authUseCase.getCurrentGamblerId()
        isAuth = CURRENT_ID.isNotBlank()
    }
}