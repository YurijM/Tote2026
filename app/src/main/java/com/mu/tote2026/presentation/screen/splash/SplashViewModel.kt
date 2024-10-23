package com.mu.tote2026.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.CURRENT_ID
import com.mu.tote2026.domain.usecase.auth_usecase.AuthUseCase
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    authUseCase: AuthUseCase,
    gamblerUseCase: GamblerUseCase
) : ViewModel() {
    var isAuth = false

    init {
        CURRENT_ID = authUseCase.getCurrentGamblerId()
        isAuth = CURRENT_ID.isNotBlank()
        gamblerUseCase.getGambler(CURRENT_ID).launchIn(viewModelScope)
    }
}