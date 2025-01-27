package com.mu.tote2026.presentation.screen.admin.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.utils.convertDateTimeToTimestamp
import com.mu.tote2026.presentation.utils.toLog
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

    init {
        gameUseCase.getGameList().onEach { gameListState ->
            _state.value = AdminGameListState(gameListState)

            if (gameListState is UiState.Success) {
                _state.value = AdminGameListState(
                    UiState.Success(gameListState.data.sortedBy { it.id.toInt() })
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminGameListEvent) {
        when (event) {
            is AdminGameListEvent.OnLoad -> {
                /*games.forEach { game ->
                    gameUseCase.deleteGame(game.id).launchIn(viewModelScope)
                }
                games.forEach { game ->
                    gameUseCase.saveGame(game).launchIn(viewModelScope)
                }*/
                games.forEach { game ->
                    toLog("game: ${game.id}")
                    gameUseCase.deleteGame(game.id).onEach { deleteGameState ->
                        toLog("deleteGame: ${game.id}")
                        if (deleteGameState is UiState.Success) {
                            gameUseCase.saveGame(game).onEach() {
                                toLog("saveGame: ${game.id}")
                            }.launchIn(viewModelScope)
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    data class AdminGameListState(
        val result: UiState<List<GameModel>> = UiState.Default
    )

    val games = listOf(
        GameModel(
            id = "1",
            start = convertDateTimeToTimestamp("14.03.2025 22:00"),
            group = "A",
            groupId = "1",
            team1 = "Германия",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Шотландия",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsco" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "2",
            start = convertDateTimeToTimestamp("15.03.2025 16:00"),
            group = "A",
            groupId = "1",
            team1 = "Венгрия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fhun" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Швейцария",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsui" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "3",
            start = convertDateTimeToTimestamp("15.03.2025 19:00"),
            group = "B",
            groupId = "2",
            team1 = "Испания",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fesp" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Хорватия",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcro" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "4",
            start = convertDateTimeToTimestamp("15.03.2025 22:00"),
            group = "B",
            groupId = "2",
            team1 = "Италия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fita" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Албания",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "5",
            start = convertDateTimeToTimestamp("16.03.2025 16:00"),
            group = "D",
            groupId = "4",
            team1 = "Польша",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpol" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Нидерланды",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fned" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "6",
            start = convertDateTimeToTimestamp("16.03.2025 19:00"),
            group = "C",
            groupId = "3",
            team1 = "Словения",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvn" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Дания",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fden" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "7",
            start = convertDateTimeToTimestamp("16.03.2025 22:00"),
            group = "C",
            groupId = "3",
            team1 = "Сербия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsrb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Англия",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Feng" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "8",
            start = convertDateTimeToTimestamp("17.03.2025 16:00"),
            group = "E",
            groupId = "5",
            team1 = "Румыния",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Frou" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Украина",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fukr" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "9",
            start = convertDateTimeToTimestamp("17.03.2025 19:00"),
            group = "E",
            groupId = "5",
            team1 = "Бельгия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Словакия",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvk" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "10",
            start = convertDateTimeToTimestamp("17.03.2025 22:00"),
            group = "D",
            groupId = "4",
            team1 = "Австрия",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Франция",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "11",
            start = convertDateTimeToTimestamp("18.03.2025 19:00"),
            group = "F",
            groupId = "6",
            team1 = "Турция",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftur" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Грузия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fgeo" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "12",
            start = convertDateTimeToTimestamp("18.03.2025 22:00"),
            group = "F",
            groupId = "6",
            team1 = "Португалия",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpor" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Чехия",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcze" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "13",
            start = convertDateTimeToTimestamp("19.03.2025 16:00"),
            group = "B",
            groupId = "2",
            team1 = "Хорватия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcro" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Албания",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "14",
            start = convertDateTimeToTimestamp("19.03.2025 19:00"),
            group = "A",
            groupId = "1",
            team1 = "Германия",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Венгрия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fhun" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "15",
            start = convertDateTimeToTimestamp("19.03.2025 22:00"),
            group = "A",
            groupId = "1",
            team1 = "Шотландия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsco" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Швейцария",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "16",
            start = convertDateTimeToTimestamp("20.03.2025 16:00"),
            group = "C",
            groupId = "3",
            team1 = "Словения",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvn" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Сербия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsrb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "17",
            start = convertDateTimeToTimestamp("20.03.2025 19:00"),
            group = "C",
            groupId = "3",
            team1 = "Дания",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fden" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Англия",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Feng" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "18",
            start = convertDateTimeToTimestamp("20.03.2025 22:00"),
            group = "B",
            groupId = "2",
            team1 = "Испания",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fesp" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Италия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fita" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "19",
            start = convertDateTimeToTimestamp("21.03.2025 16:00"),
            group = "E",
            groupId = "5",
            team1 = "Словакия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvk" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Украина",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fukr" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "20",
            start = convertDateTimeToTimestamp("21.03.2025 19:00"),
            group = "D",
            groupId = "4",
            team1 = "Польша",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpol" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Австрия",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "21",
            start = convertDateTimeToTimestamp("21.03.2025 22:00"),
            group = "D",
            groupId = "4",
            team1 = "Нидерланды",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fned" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Франция",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "22",
            start = convertDateTimeToTimestamp("22.03.2025 16:00"),
            group = "F",
            groupId = "6",
            team1 = "Грузия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fgeo" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Чехия",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcze" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "23",
            start = convertDateTimeToTimestamp("22.03.2025 19:00"),
            group = "F",
            groupId = "6",
            team1 = "Турция",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftur" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Португалия",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpor" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "24",
            start = convertDateTimeToTimestamp("22.03.2025 22:00"),
            group = "E",
            groupId = "5",
            team1 = "Бельгия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Румыния",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Frou" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "25",
            start = convertDateTimeToTimestamp("23.03.2025 22:00"),
            group = "A",
            groupId = "1",
            team1 = "Швейцария",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsui" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Германия",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "26",
            start = convertDateTimeToTimestamp("23.03.2025 22:00"),
            group = "A",
            groupId = "1",
            team1 = "Шотландия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsco" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Венгрия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fhun" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "27",
            start = convertDateTimeToTimestamp("24.03.2025 22:00"),
            group = "B",
            groupId = "2",
            team1 = "Албания",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Falb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Испания",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fesp" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "28",
            start = convertDateTimeToTimestamp("24.03.2025 22:00"),
            group = "B",
            groupId = "2",
            team1 = "Хорватия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcro" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Италия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fita" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "29",
            start = convertDateTimeToTimestamp("25.03.2025 19:00"),
            group = "D",
            groupId = "4",
            team1 = "Нидерланды",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fned" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Австрия",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Faut" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "30",
            start = convertDateTimeToTimestamp("25.03.2025 19:00"),
            group = "D",
            groupId = "4",
            team1 = "Франция",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Польша",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpol" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "31",
            start = convertDateTimeToTimestamp("25.03.2025 22:00"),
            group = "C",
            groupId = "3",
            team1 = "Англия",
            team1ItemNo = "4",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Feng" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Словения",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvn" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "32",
            start = convertDateTimeToTimestamp("25.03.2025 22:00"),
            group = "C",
            groupId = "3",
            team1 = "Дания",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fden" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Сербия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsrb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "33",
            start = convertDateTimeToTimestamp("26.03.2025 19:00"),
            group = "E",
            groupId = "5",
            team1 = "Словакия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsvk" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Румыния",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Frou" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
        ),
        GameModel(
            id = "34",
            start = convertDateTimeToTimestamp("26.03.2025 19:00"),
            group = "E",
            groupId = "5",
            team1 = "Украина",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fukr" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Бельгия",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "35",
            start = convertDateTimeToTimestamp("26.03.2025 22:00"),
            group = "F",
            groupId = "6",
            team1 = "Грузия",
            team1ItemNo = "3",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fgeo" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Португалия",
            team2ItemNo = "1",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpor" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "36",
            start = convertDateTimeToTimestamp("26.03.2025 22:00"),
            group = "F",
            groupId = "6",
            team1 = "Чехия",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fcze" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Турция",
            team2ItemNo = "4",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ftur" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        /*GameModel(
            id = "37",
            start = convertDateTimeToTimestamp("28.03.2025 19:00"),
            group = "1/8 финала",
            groupId = "9",
            team1 = "Германия",
            team1ItemNo = "",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fger" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Франция",
            team2ItemNo = "",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Ffra" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
        ),
        GameModel(
            id = "38",
            start = convertDateTimeToTimestamp("28.03.2025 22:00"),
            group = "1/8 финала",
            groupId = "9",
            team1 = "Дания",
            team1ItemNo = "",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fden" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Румыния",
            team2ItemNo = "",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Frou" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
        ),
        GameModel(
            id = "39",
            start = convertDateTimeToTimestamp("28.03.2025 22:00"),
            group = "1/4 финала",
            groupId = "10",
            team1 = "Сербия",
            team1ItemNo = "",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsrb" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7",
            team2 = "Бельгия",
            team2ItemNo = "",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fbel" +
                    ".png?alt=media&token=8d3ae0ab-1fb4-46ed-9805-7cb962f1a7f7"
        ),*/
    )
}