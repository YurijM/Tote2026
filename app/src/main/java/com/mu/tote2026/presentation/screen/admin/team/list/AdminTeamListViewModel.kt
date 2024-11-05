package com.mu.tote2026.presentation.screen.admin.team.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.usecase.team_usecase.TeamUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminTeamListViewModel @Inject constructor(
    private val teamUseCase: TeamUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminTeamListState())
    val state = _state.asStateFlow()

    init {
        teamUseCase.getTeamList().onEach { teamListState ->
            _state.value = AdminTeamListState(teamListState)

            if (teamListState is UiState.Success) {
                _state.value = AdminTeamListState(
                    UiState.Success(teamListState.data.sortedBy { it.team })
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminTeamListEvent) {
        when (event) {
            is AdminTeamListEvent.OnLoad -> {
                viewModelScope.launch {
                    teams.forEach { team ->
                        teamUseCase.deleteTeam(team.team).launchIn(viewModelScope)
                    }
                    teams.forEach { team ->
                        teamUseCase.saveTeam(team).launchIn(viewModelScope)
                    }
                }
            }
        }
    }

    companion object {
        data class AdminTeamListState(
            val result: UiState<List<TeamModel>> = UiState.Default
        )

        val teams = listOf(
            TeamModel(
                group = "A",
                itemNo = "1",
                team = "Германия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "A",
                itemNo = "2",
                team = "Шотландия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsco.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "A",
                itemNo = "3",
                team = "Венгрия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fhun.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "A",
                itemNo = "4",
                team = "Швейцария",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsui.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "B",
                itemNo = "1",
                team = "Испания",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fesp.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "B",
                itemNo = "2",
                team = "Хорватия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcro.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "B",
                itemNo = "3",
                team = "Италия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fita.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "B",
                itemNo = "4",
                team = "Албания",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falb.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "C",
                itemNo = "1",
                team = "Словения",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvn.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "C",
                itemNo = "2",
                team = "Дания",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fden.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "C",
                itemNo = "3",
                team = "Сербия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsrb.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "C",
                itemNo = "4",
                team = "Англия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Feng.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "D",
                itemNo = "1",
                team = "Нидерланды",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fned.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "D",
                itemNo = "2",
                team = "Франция",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "D",
                itemNo = "3",
                team = "Польша",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpol.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "D",
                itemNo = "4",
                team = "Австрия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "E",
                itemNo = "1",
                team = "Украина",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fukr.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "E",
                itemNo = "2",
                team = "Словакия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvk.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "E",
                itemNo = "3",
                team = "Бельгия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "E",
                itemNo = "4",
                team = "Румыния",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Frou.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "F",
                itemNo = "1",
                team = "Португалия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpor.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "F",
                itemNo = "2",
                team = "Чехия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcze.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "F",
                itemNo = "3",
                team = "Грузия",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fgeo.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            TeamModel(
                group = "F",
                itemNo = "4",
                team = "Турция",
                flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftur.png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
        )
    }

}