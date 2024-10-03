package com.mu.tote2026.presentation.screen.admin.group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.domain.usecase.group_usecase.GroupUseCase
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupUseCase: GroupUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminGroupState())
    val state = _state.asStateFlow()

    var group by mutableStateOf(GroupModel())
        private set

    var id by mutableStateOf("")
        private set
    var idError by mutableStateOf("")
    var groupError by mutableStateOf("")

    var enabledSaveButton by mutableStateOf(false)
        private set

    var exit by mutableStateOf(false)

    init {
        val id = savedStateHandle.get<String>(KEY_ID)
        toLog("AdminGroupViewModel id: $id")

        if (!id.isNullOrBlank() && id != NEW_DOC) {
            groupUseCase.getGroup(id).onEach { groupState ->
                _state.value = AdminGroupState(groupState)

                if (groupState is UiState.Success) {
                    group = groupState.data
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: AdminGroupEvent) {
        when (event) {
            is AdminGroupEvent.OnIdChange -> {
                group = group.copy(id = event.id)
                enabledSaveButton = checkValues()
            }
            is AdminGroupEvent.OnGroupChange -> {
                group = group.copy(group = event.group)
                enabledSaveButton = checkValues()
            }
            is AdminGroupEvent.OnSave -> {
                groupUseCase.saveGroup(group).onEach { groupState ->
                    _state.value = AdminGroupState(groupState)

                    if (groupState is UiState.Success) exit = true
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun checkValues(): Boolean {
        idError = checkIsFieldEmpty(group.id)
        groupError = checkIsFieldEmpty(group.group)

        return idError.isBlank() &&
                groupError.isBlank()
    }

    companion object {
        data class AdminGroupState(
            val result: UiState<GroupModel> = UiState.Default
        )
    }
}