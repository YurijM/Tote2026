package com.mu.tote2026.presentation.screen.admin.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.screen.admin.team.list.AdminTeamListViewModel.Companion.teams
import com.mu.tote2026.presentation.utils.convertDateTimeToTimestamp
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminGameListViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminGameListState())
    val state = _state.asStateFlow()

    var gameList = mutableListOf<GameModel>()

    init {
        gameUseCase.getGameList().onEach { gameListState ->
            _state.value = AdminGameListState(gameListState)

            if (gameListState is UiState.Success) {
                val games = gameListState.data
                gameList = games.sortedBy { it.id }.toMutableList()
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminGameListEvent) {
        when (event) {
            is AdminGameListEvent.OnLoad -> {
                gameList.forEach { game ->
                    gameUseCase.deleteGame(game.id).launchIn(viewModelScope)
                }
                games.forEach { game ->
                    gameUseCase.saveGame(game).launchIn(viewModelScope)
                }
            }
        }
    }

    companion object {
        data class AdminGameListState(
            val result: UiState<List<GameModel>> = UiState.Default
        )

        val games = listOf(
            GameModel(
                id = "1",
                start = convertDateTimeToTimestamp("14.06.2024 22:00"),
                group = "A",
                groupId = "1",
                team1 = "Германия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fger" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Шотландия",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fsco" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "2",
                start = convertDateTimeToTimestamp("15.06.2024 16:00"),
                group = "A",
                groupId = "1",
                team1 = "Венгрия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fhun" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Швейцария",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fsui" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "3",
                start = convertDateTimeToTimestamp("15.06.2024 19:00"),
                group = "B",
                groupId = "2",
                team1 = "Испания",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fesp" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Хорватия",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fcro" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "4",
                start = convertDateTimeToTimestamp("15.06.2024 22:00"),
                group = "B",
                groupId = "2",
                team1 = "Италия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fita" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Албания",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Falb" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "5",
                start = convertDateTimeToTimestamp("16.06.2024 16:00"),
                group = "D",
                groupId = "4",
                team1 = "Польша",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fpol" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Нидерланды",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fned" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "6",
                start = convertDateTimeToTimestamp("16.06.2024 19:00"),
                group = "C",
                groupId = "3",
                team1 = "Словения",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fsvn" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Дания",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fden" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            /*GameModel(
                id = "7",
                start = convertDateTimeToTimestamp("16.06.2024 22:00"),
                group = "C",
                team1 = "Сербия",
                team2 = "Англия"
            ),
            GameModel(
                id = "8",
                start = convertDateTimeToTimestamp("17.06.2024 16:00"),
                group = "E",
                team1 = "Румыния",
                team2 = "Украина"
            ),
            GameModel(
                id = "9",
                start = convertDateTimeToTimestamp("17.06.2024 19:00"),
                group = "E",
                team1 = "Бельгия",
                team2 = "Словакия"
            ),
            GameModel(
                id = "10",
                start = convertDateTimeToTimestamp("17.06.2024 22:00"),
                group = "D",
                team1 = "Австрия",
                team2 = "Франция"
            ),
            GameModel(
                id = "11",
                start = convertDateTimeToTimestamp("18.06.2024 19:00"),
                group = "F",
                team1 = "Турция",
                team2 = "Грузия"
            ),
            GameModel(
                id = "12",
                start = convertDateTimeToTimestamp("18.06.2024 22:00"),
                group = "F",
                team1 = "Португалия",
                team2 = "Чехия"
            ),
            GameModel(
                id = "13",
                start = convertDateTimeToTimestamp("19.06.2024 16:00"),
                group = "B",
                team1 = "Хорватия",
                team2 = "Албания"
            ),
            GameModel(
                id = "14",
                start = convertDateTimeToTimestamp("19.06.2024 19:00"),
                group = "A",
                team1 = "Германия",
                team2 = "Венгрия"
            ),
            GameModel(
                id = "15",
                start = convertDateTimeToTimestamp("19.06.2024 22:00"),
                group = "A",
                team1 = "Шотландия",
                team2 = "Швейцария"
            ),
            GameModel(
                id = "16",
                start = convertDateTimeToTimestamp("20.06.2024 16:00"),
                group = "C",
                team1 = "Словения",
                team2 = "Сербия"
            ),
            GameModel(
                id = "17",
                start = convertDateTimeToTimestamp("20.06.2024 19:00"),
                group = "C",
                team1 = "Дания",
                team2 = "Англия"
            ),
            GameModel(
                id = "18",
                start = convertDateTimeToTimestamp("20.06.2024 22:00"),
                group = "B",
                team1 = "Испания",
                team2 = "Италия"
            ),
            GameModel(
                id = "19",
                start = convertDateTimeToTimestamp("21.06.2024 16:00"),
                group = "E",
                team1 = "Словакия",
                team2 = "Украина"
            ),
            GameModel(
                id = "20",
                start = convertDateTimeToTimestamp("21.06.2024 19:00"),
                group = "D",
                team1 = "Польша",
                team2 = "Австрия"
            ),
            GameModel(
                id = "21",
                start = convertDateTimeToTimestamp("21.06.2024 22:00"),
                group = "D",
                team1 = "Нидерланды",
                team2 = "Франция"
            ),
            GameModel(
                id = "22",
                start = convertDateTimeToTimestamp("22.06.2024 16:00"),
                group = "F",
                team1 = "Грузия",
                team2 = "Чехия"
            ),
            GameModel(
                id = "23",
                start = convertDateTimeToTimestamp("22.06.2024 19:00"),
                group = "F",
                team1 = "Турция",
                team2 = "Португалия"
            ),
            GameModel(
                id = "24",
                start = convertDateTimeToTimestamp("22.06.2024 22:00"),
                group = "E",
                team1 = "Бельгия",
                team2 = "Румыния"
            ),
            GameModel(
                id = "25",
                start = convertDateTimeToTimestamp("23.06.2024 22:00"),
                group = "A",
                team1 = "Швейцария",
                team2 = "Германия"
            ),
            GameModel(
                id = "26",
                start = convertDateTimeToTimestamp("23.06.2024 22:00"),
                group = "A",
                team1 = "Шотландия",
                team2 = "Венгрия"
            ),
            GameModel(
                id = "27",
                start = convertDateTimeToTimestamp("24.06.2024 22:00"),
                group = "B",
                team1 = "Албания",
                team2 = "Испания"
            ),
            GameModel(
                id = "28",
                start = convertDateTimeToTimestamp("24.06.2024 22:00"),
                group = "B",
                team1 = "Хорватия",
                team2 = "Италия"
            ),
            GameModel(
                id = "29",
                start = convertDateTimeToTimestamp("25.06.2024 19:00"),
                group = "D",
                team1 = "Нидерланды",
                team2 = "Австрия"
            ),
            GameModel(
                id = "30",
                start = convertDateTimeToTimestamp("25.06.2024 19:00"),
                group = "D",
                team1 = "Франция",
                team2 = "Польша"
            ),
            GameModel(
                id = "31",
                start = convertDateTimeToTimestamp("25.06.2024 22:00"),
                group = "C",
                team1 = "Англия",
                team2 = "Словения"
            ),
            GameModel(
                id = "32",
                start = convertDateTimeToTimestamp("25.06.2024 22:00"),
                group = "C",
                team1 = "Дания",
                team2 = "Сербия"
            ),
            GameModel(
                id = "33",
                start = convertDateTimeToTimestamp("26.06.2024 19:00"),
                group = "E",
                groupId = "5",
                team1 = "Словакия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fsvk" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Румыния",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Frum" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
            ),
            GameModel(
                id = "34",
                start = convertDateTimeToTimestamp("26.06.2024 19:00"),
                group = "E",
                groupId = "5",
                team1 = "Украина",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fukr" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Бельгия",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fbel" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "35",
                start = convertDateTimeToTimestamp("26.06.2024 22:00"),
                group = "F",
                groupId = "6",
                team1 = "Грузия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fbel" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Португалия",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fbel" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),
            GameModel(
                id = "36",
                start = convertDateTimeToTimestamp("26.06.2024 22:00"),
                group = "F",
                groupId = "6",
                team1 = "Чехия",
                flag1 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Fbel" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
                team2 = "Турция",
                flag2 = "https://firebasestorage.googleapis.com/v0/b/tote-2024-e3d04.appspot.com/o/flags%2Ftur" +
                        ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            ),*/
        )
    }
}