package com.mu.tote2026.presentation.screen.profile

import androidx.lifecycle.ViewModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.presentation.screen.auth.signIn.SignInViewModel.Companion.SignInState
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()


    companion object {
        data class ProfileState(
            val result: UiState<GamblerModel> = UiState.Default,
        )
    }
}