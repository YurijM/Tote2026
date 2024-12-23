package com.mu.tote2026.presentation.screen.admin.stake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.gambler_usecase.GamblerUseCase
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class AdminStakeListViewModel @Inject constructor(
    gameUseCase: GameUseCase,
    private val gamblerUseCase: GamblerUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminStakeListState())
    val state = _state.asStateFlow()

    var games = mutableListOf<GameModel>()
    var gamblers = listOf<GamblerModel>()

    init {
        gameUseCase.getGameList().onEach { gameListState ->
            _state.value = AdminStakeListState(gameListState)

            if (gameListState is UiState.Success) {
                games = gameListState.data
                    .filter { it.start.toLong() > currentTimeMillis() }
                    .sortedBy { it.id.toInt() }.toMutableList()

                _state.value = AdminStakeListState(
                    UiState.Success(games)
                )

                gamblerUseCase.getGamblerList().onEach { gamblerListState ->
                    if (gamblerListState is UiState.Success) {
                        gamblers = gamblerListState.data
                            .filter { it.rate > 0 }
                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }

    /*fun onEvent(event: AdminStakeListEvent) {
        when (event) {
            is AdminStakeListEvent.OnLoad -> {
                games.forEach { game ->
                    gameUseCase.deleteStakes(game.id).launchIn(viewModelScope)
                    gamblers.forEach { gambler ->
                        val stake = randomStake(game, gambler)
                        gameUseCase.saveStake(StakeModel(gameId = game.id), stake).launchIn(viewModelScope)
                    }
                }
            }
        }
    }*/

    /*private fun randomStake(game: GameModel, gambler: GamblerModel): StakeModel {
        val goal1 = (0..3).random()
        val goal2 = (0..3).random()
        val result = when {
            goal1 > goal2 -> WIN
            goal1 == goal2 -> DRAW
            else -> DEFEAT
        }

        var addGoal1 = ""
        var addGoal2 = ""
        var addResult = ""
        var byPenalty = ""

        if (game.groupId.toInt() > GROUPS_COUNT) {
            if (goal1 == goal2) {
                addGoal1 = (goal1..3).random().toString()
                addGoal2 = (goal2..3).random().toString()
                addResult = when {
                    addGoal1 > addGoal2 -> WIN
                    addGoal1 == addGoal2 -> {
                        byPenalty = (1..2).random().toString()
                        DRAW
                    }
                    else -> DEFEAT
                }
            }
        }

        return StakeModel(
            gameId = game.id,
            gamblerId = gambler.id,
            gamblerNickname = gambler.nickname,
            gamblerRatePercent = gambler.ratePercent,
            goal1 = goal1.toString(),
            goal2 = goal2.toString(),
            result = result,
            addGoal1 = addGoal1,
            addGoal2 = addGoal2,
            addResult = addResult,
            byPenalty = byPenalty
        )
    }*/

    data class AdminStakeListState(
        val result: UiState<List<GameModel>> = UiState.Default
    )
}