package com.mu.tote2026.presentation.screen.admin.team

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.usecase.team_usecase.TeamUseCase
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.checkIsFieldEmpty
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminTeamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teamUseCase: TeamUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AdminTeamState> = MutableStateFlow(AdminTeamState())
    val state = _state.asStateFlow()

    var team by mutableStateOf(TeamModel())
        private set
    var isEdit = true
        private set

    private var flagUri by mutableStateOf<Uri?>(null)
    private var currentFlag by mutableStateOf("")

    var enabledSaveButton by mutableStateOf(false)
        private set

    var teamError by mutableStateOf("")
    var groupError by mutableStateOf("")
    var itemNoError by mutableStateOf("")
    var flagUrlError by mutableStateOf("")

    var exit by mutableStateOf(false)

    init {
        val id = savedStateHandle.get<String>(KEY_ID)

        if (!id.isNullOrBlank() && id != NEW_DOC) {
            teamUseCase.getTeam(id).onEach { teamState ->
                _state.value = AdminTeamState(teamState)

                if (teamState is UiState.Success) {
                    team = teamState.data
                    currentFlag = team.flag
                    enabledSaveButton = checkValues()
                    isEdit = team.team.isEmpty()
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: AdminTeamEvent) {
        when (event) {
            is AdminTeamEvent.OnTeamChange -> {
                team = team.copy(team = event.team)
                enabledSaveButton = checkValues()
            }

            is AdminTeamEvent.OnGroupChange -> {
                team = team.copy(group = event.group)
                enabledSaveButton = checkValues()
            }

            is AdminTeamEvent.OnItemNoChange -> {
                team = team.copy(itemNo = event.itemNo)
                enabledSaveButton = checkValues()
            }

            is AdminTeamEvent.OnFlagChange -> {
                team = team.copy(flag = event.uri.toString())
                flagUri = event.uri
                enabledSaveButton = checkValues()
            }

            is AdminTeamEvent.OnSave -> {
                //if (flagUri != null && team.flag != currentFlag) {
                if (flagUri != null) {
                    teamUseCase.saveTeamFlag(team.team, flagUri!!).onEach { flagState ->
                        if (flagState is UiState.Success) {
                            team = team.copy(flag = flagState.data)
                            exit = true
                            teamUseCase.saveTeam(team).onEach { teamState ->
                                _state.value = AdminTeamState(teamState)
                            }.launchIn(viewModelScope)
                        } else if (flagState is UiState.Error) {
                            _state.value = AdminTeamState(UiState.Error(flagState.error))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    teamUseCase.saveTeam(team).onEach { teamState ->
                        _state.value = AdminTeamState(teamState)
                        if (teamState is UiState.Success) {
                            exit = true
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private fun checkValues(): Boolean {
        teamError = checkIsFieldEmpty(team.team)
        groupError = checkIsFieldEmpty(team.group)
        itemNoError = checkIsFieldEmpty(team.itemNo)
        flagUrlError = checkIsFieldEmpty(team.flag)

        return teamError.isBlank() &&
                groupError.isBlank() &&
                itemNoError.isBlank() &&
                flagUrlError.isBlank()
    }

    data class AdminTeamState(
        val result: UiState<TeamModel> = UiState.Default
    )
}