package com.mu.tote2026.presentation.screen.admin.group.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.domain.usecase.group_usecase.GroupUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminGroupListViewModel @Inject constructor(
    private val groupUseCase: GroupUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminGroupListState())
    val state = _state.asStateFlow()

    var groupList = mutableListOf<GroupModel>()
    var message = mutableStateOf("")
        private set

    init {
        groupUseCase.getGroupList().onEach { groupListState ->
            _state.value = AdminGroupListState(groupListState)
            if (groupListState is UiState.Success) {
                groupList = groupListState.data.toMutableList()
                groupList.sortBy { it.id }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminGroupListEvent) {
        when (event) {
            is AdminGroupListEvent.OnDelete -> {
                groupUseCase.deleteGroup(event.group.id.toString()).onEach { deleteGroupState ->
                    if (deleteGroupState is UiState.Success)
                        message.value = "Группа ${event.group.group} удалена из списка"
                }.launchIn(viewModelScope)
            }
        }
    }

    companion object {
        data class AdminGroupListState(
            val result: UiState<List<GroupModel>> = UiState.Default
        )
    }
}