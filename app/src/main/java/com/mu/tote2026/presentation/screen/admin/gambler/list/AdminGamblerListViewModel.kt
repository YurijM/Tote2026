package com.mu.tote2026.presentation.screen.admin.gambler.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.data.repository.GROUP_GAMES_COUNT
import com.mu.tote2026.data.repository.PLAYOFF_GAMES_COUNT
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.model.GamblerModel
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
class AdminGamblerListViewModel @Inject constructor(
    private val gamblerUseCase: GamblerUseCase,
    private val gameUseCase: GameUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<AdminGamblerListState> = MutableStateFlow(AdminGamblerListState())
    val state = _state.asStateFlow()

    var prizeFund = 0
    var winnerPlayed by mutableDoubleStateOf(0.0)
        private set
    var restedPrizeFund by mutableDoubleStateOf(0.0)
        private set
    private var matchesPlayed = 0
    private var matchesPlayedbyGroup = 0
    private var matchesPlayedbyPlayoff = 0
    private var commonParams = CommonParamsModel()

    init {
        gamblerUseCase.getGamblerList().onEach { gamblerListState ->
            if (gamblerListState is UiState.Success) {
                val gamblers = AdminGamblerListState(
                    UiState.Success(gamblerListState.data.sortedBy { it.nickname })
                )
                _state.value = gamblers
                prizeFund = gamblerListState.data.sumOf { it.rate }
                calc()
            } else {
                _state.value = AdminGamblerListState(gamblerListState)
            }
        }.launchIn(viewModelScope)
    }

    private fun calc() {
        gameUseCase.getGameList().onEach { gameListState ->
            if (gameListState is UiState.Success) {
                matchesPlayed = gameListState.data.filter { it.start.toLong() < currentTimeMillis() }.size
                if (matchesPlayed > GROUP_GAMES_COUNT) {
                    matchesPlayedbyGroup = GROUP_GAMES_COUNT
                    matchesPlayedbyPlayoff = matchesPlayed - matchesPlayedbyGroup
                } else {
                    matchesPlayedbyGroup = matchesPlayed
                    matchesPlayedbyPlayoff = 0
                }

                gamblerUseCase.getCommonParams().onEach { commonParamsState ->
                    if (commonParamsState is UiState.Success) {
                        commonParams = commonParamsState.data
                        winnerPlayed = matchesPlayedbyGroup * (commonParams.groupPrizeFund / GROUP_GAMES_COUNT) +
                                matchesPlayedbyPlayoff * (commonParams.playoffPrizeFund / PLAYOFF_GAMES_COUNT) +
                                commonParams.winnersPrizeFundByStake +
                                commonParams.winnersPrizeFund
                        restedPrizeFund = commonParams.prizeFund -
                                matchesPlayedbyGroup * (commonParams.groupPrizeFund / GROUP_GAMES_COUNT) -
                                matchesPlayedbyPlayoff * (commonParams.playoffPrizeFund / PLAYOFF_GAMES_COUNT) -
                                commonParams.winnersPrizeFundByStake -
                                commonParams.winnersPrizeFund
                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }

    data class AdminGamblerListState(
        val result: UiState<List<GamblerModel>> = UiState.Default
    )
}