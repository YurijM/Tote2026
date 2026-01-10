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
            start = convertDateTimeToTimestamp("11.06.2026 22:00"),
            group = "A",
            groupId = "1",
            team1 = "Мексика",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fmex.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
            team2 = "ЮАР",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fsaf.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
        ),
        GameModel(
            id = "4",
            start = convertDateTimeToTimestamp("13.06.2026 04:00"),
            group = "D",
            groupId = "4",
            team1 = "США",
            team1ItemNo = "1",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fusa.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
            team2 = "Парагвай",
            team2ItemNo = "2",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fpar.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
        ),
        GameModel(
            id = "6",
            start = convertDateTimeToTimestamp("13.06.2026 22:00"),
            group = "B",
            groupId = "2",
            team1 = "Катар",
            team1ItemNo = "2",
            flag1 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fqat.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
            team2 = "Швейцария",
            team2ItemNo = "3",
            flag2 = "https://firebasestorage.googleapis.com/v0/b/tote2026-d3cab.appspot.com/o/flags%2Fswi.png?alt=media&token=c54503ca-3b35-4018-97cc-4eb58b42e6e4",
        ),
    )
}