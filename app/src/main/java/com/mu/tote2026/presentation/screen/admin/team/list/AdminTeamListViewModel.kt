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

    data class AdminTeamListState(
        val result: UiState<List<TeamModel>> = UiState.Default
    )

    val teams = listOf(
        TeamModel(
            group = "A",
            itemNo = "1",
            team = "Мексика",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fmex.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "A",
            itemNo = "2",
            team = "ЮАР",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsaf.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "A",
            itemNo = "3",
            team = "Южная Корея",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fkor.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "A",
            itemNo = "4",
            team = "Швейцария",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsui.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "B",
            itemNo = "1",
            team = "Канада",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcan.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "B",
            itemNo = "2",
            team = "Катар",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fqat.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "B",
            itemNo = "3",
            team = "Швейцария",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fswi.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "B",
            itemNo = "4",
            team = "Албания",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falb.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "C",
            itemNo = "1",
            team = "Бразилия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbra.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "C",
            itemNo = "2",
            team = "Марокко",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fmar.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "C",
            itemNo = "3",
            team = "Гаити",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fhai.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "C",
            itemNo = "4",
            team = "Шотландия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsco.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "D",
            itemNo = "1",
            team = "США",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fusa.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "D",
            itemNo = "2",
            team = "Парагвай",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpar.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "D",
            itemNo = "3",
            team = "Австралия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faus.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "D",
            itemNo = "4",
            team = "Австрия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "E",
            itemNo = "1",
            team = "Германия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "E",
            itemNo = "2",
            team = "Кюрасао",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcur.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "E",
            itemNo = "3",
            team = "Кот-д'Ивуар",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fivo.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "E",
            itemNo = "4",
            team = "Эквадор",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fecu.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "F",
            itemNo = "1",
            team = "Нидерланды",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fnet.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "F",
            itemNo = "2",
            team = "Япония",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fjap.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "F",
            itemNo = "3",
            team = "Тунис",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftun.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "F",
            itemNo = "4",
            team = "Турция",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftur.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "G",
            itemNo = "1",
            team = "Бельгия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "G",
            itemNo = "2",
            team = "Египет",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fegy.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "G",
            itemNo = "3",
            team = "Иран",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fira.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "G",
            itemNo = "4",
            team = "Новая Зеландия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fnze.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "H",
            itemNo = "1",
            team = "Испания",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fspa.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "H",
            itemNo = "2",
            team = "Кабо-Верде",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcve.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "H",
            itemNo = "3",
            team = "Саудовская Аравия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsar.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "H",
            itemNo = "4",
            team = "Уругвай",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Furu.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "I",
            itemNo = "1",
            team = "Франция",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "I",
            itemNo = "2",
            team = "Сенегал",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsen.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "I",
            itemNo = "3",
            team = "Норвегия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fnor.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "I",
            itemNo = "4",
            team = "Норвегия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fnor.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "J",
            itemNo = "1",
            team = "Аргентина",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Farg.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "J",
            itemNo = "2",
            team = "Алжир",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falg.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "J",
            itemNo = "3",
            team = "Австрия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "J",
            itemNo = "4",
            team = "Иордания",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fjor.png?alt=media&token=00313549-9495-4def-a3b1-ef7d7d99904a"
        ),
        TeamModel(
            group = "K",
            itemNo = "1",
            team = "Португалия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpor.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "K",
            itemNo = "2",
            team = "Узбекистан",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fuzb.png?alt=media&token=3e6e48df-e1e9-4322-91f6-1f8ffb45fa7c"
        ),
        TeamModel(
            group = "K",
            itemNo = "3",
            team = "Колумбия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcol.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        /*TeamModel(
            group = "K",
            itemNo = "4",
            team = "Колумбия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcol.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),*/
        TeamModel(
            group = "L",
            itemNo = "1",
            team = "Англия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Feng.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "L",
            itemNo = "2",
            team = "Хорватия",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcro.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "L",
            itemNo = "3",
            team = "Гана",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fgha.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
        TeamModel(
            group = "L",
            itemNo = "4",
            team = "Панама",
            flag = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpan.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4"
        ),
    )
}