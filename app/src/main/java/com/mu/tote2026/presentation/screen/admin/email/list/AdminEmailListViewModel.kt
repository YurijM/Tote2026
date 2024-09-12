package com.mu.tote2026.presentation.screen.admin.email.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.usecase.email_usecase.EmailUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminEmailListViewModel @Inject constructor(
    private val emailUseCase: EmailUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminEmailListState> = MutableStateFlow(AdminEmailListState())
    val state = _state.asStateFlow()

    var emailList = mutableListOf<EmailModel>()
    var message = mutableStateOf("")
        private set

    init {
        emailUseCase.getEmailList().onEach { emailListState ->
            _state.value = AdminEmailListState(emailListState)

            if (emailListState is UiState.Success) {
                emailList = emailListState.data.toMutableList()
                emailList.sortBy { it.email }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminEmailListEvent) {
        when (event) {
            is AdminEmailListEvent.OnDelete -> {
                emailUseCase.deleteEmail(event.email).onEach { deleteState ->
                    if (deleteState is UiState.Success) {
                        message.value = "Email ${event.email.email} удалён из списка"
                    } else if (deleteState is UiState.Error) {
                        message.value = deleteState.error
                    }
                }.launchIn(viewModelScope)

                message.value = ""
            }
        }
    }

    companion object {
        data class AdminEmailListState(
            val result: UiState<List<EmailModel>> = UiState.Default
        )
    }
}
