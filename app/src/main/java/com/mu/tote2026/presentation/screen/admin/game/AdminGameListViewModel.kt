package com.mu.tote2026.presentation.screen.admin.game

import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.usecase.game_usecase.GameUseCase
import com.mu.tote2026.presentation.utils.DIR_DOCS
import com.mu.tote2026.presentation.utils.createExtFile
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AdminGameListViewModel @Inject constructor(
    val gameUseCase: GameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminGameListState())
    val state = _state.asStateFlow()

    var games by mutableStateOf<List<GameModel>>(listOf())

    init {
        gameUseCase.getGameList().onEach { gameListState ->
            _state.value = AdminGameListState(gameListState)

            if (gameListState is UiState.Success) {
                games = gameListState.data.sortedBy { it.id.toInt() }
                _state.value = AdminGameListState(
                    //UiState.Success(gameListState.data.sortedBy { it.id.toInt() })
                    UiState.Success(games)
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AdminGameListEvent) {
        when (event) {
            is AdminGameListEvent.OnLoad -> {
                var writeResult = ""
                val filename = "games.txt"

                val fullPath = "${Environment.DIRECTORY_DOWNLOADS}/ $DIR_DOCS/ $filename"

                val file = createExtFile(filename)
                var fileInputStream: FileInputStream? = null

                try {
                    fileInputStream = file.inputStream()
                    //val lines = mutableListOf<String>()

                    var game = GameModel()

                    file.bufferedReader().useLines { lines ->
                        lines.forEach { line ->
                            if (line.contains("GameModel")) {
                                game = GameModel()
                            } else if (line.contains(")")) {
                                gameUseCase.saveGame(game).onEach { gameSaveState ->
                                    when (gameSaveState) {
                                        is UiState.Success -> {
                                            toLog("Игра ${game.id} добавлена")
                                        }
                                        is UiState.Error -> {
                                            val error = UiState.Error(gameSaveState.error)
                                            Toast.makeText(event.context, error.error, Toast.LENGTH_LONG).show()
                                            toLog("error: ${error.error}")
                                        }
                                        else -> {}
                                    }
                                }.launchIn(viewModelScope)
                            } else {
                                val start = line.indexOf(" = ")
                                val end = line.indexOf(",")
                                val field = line.substring(0, start).trim()
                                val value = line.substring(start + 3, end).trim()

                                when (field) {
                                    "id" -> game = game.copy(id = value)
                                    "start" -> game = game.copy(start = value)
                                    "group" -> game = game.copy(group = value)
                                    "groupId" -> game = game.copy(groupId = value)
                                    "team1" -> game = game.copy(team1 = value)
                                    "team1ItemNo" -> game = game.copy(team1ItemNo = value)
                                    "flag1" -> game = game.copy(flag1 = value)
                                    "team2" -> game = game.copy(team2 = value)
                                    "team2ItemNo" -> game = game.copy(team2ItemNo = value)
                                    "flag2" -> game = game.copy(flag2 = value)
                                }
                            }
                        }
                    }
                    writeResult = "Файл $fullPath загружен"
                } catch (e: Exception) {
                    //e.printStackTrace()
                    writeResult = "Ошибка ${e.message}"
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close()
                        } catch (e: IOException) {
                            //e.printStackTrace()
                            writeResult = "Ошибка ${e.message}"
                        }
                    }
                    if (writeResult.isNotBlank()) {
                        Toast.makeText(event.context, writeResult, Toast.LENGTH_LONG).show()
                    }
                }

                /*games.forEach { game ->
                    toLog("game: ${game.id}")
                    gameUseCase.deleteGame(game.id).onEach { deleteGameState ->
                        toLog("deleteGame: ${game.id}")
                        if (deleteGameState is UiState.Success) {
                            gameUseCase.saveGame(game).onEach {
                                toLog("saveGame: ${game.id}")
                            }.launchIn(viewModelScope)
                        }
                    }.launchIn(viewModelScope)
                }*/
            }

            is AdminGameListEvent.OnUnload -> {
                var writeResult = ""
                val filename = "games.txt"

                val file = createExtFile(filename, "", true)

                val fullPath = "${Environment.DIRECTORY_DOWNLOADS}/ $DIR_DOCS/ $filename"

                var fileOutputStream: FileOutputStream? = null
                try {
                    fileOutputStream = FileOutputStream(file, true)

                    games.forEach { game ->
                        //val data = "${game.team1} - ${game.team2}\n"
                        val data = "GameModel(\n" +
                                "\tid = ${game.id},\n" +
                                "\tstart = ${game.start},\n" +
                                "\tgroup = ${game.group},\n" +
                                "\tgroupId = ${game.groupId},\n" +
                                "\tteam1 = ${game.team1},\n" +
                                "\tteam1ItemNo = ${game.team1ItemNo},\n" +
                                "\tflag1 = ${game.flag1},\n" +
                                "\tteam2 = ${game.team2},\n" +
                                "\tteam2ItemNo = ${game.team2ItemNo},\n" +
                                "\tflag2 = ${game.flag2},\n" +
                                "),\n"
                        fileOutputStream.write(data.toByteArray())
                    }
                    writeResult = "Создан файл $fullPath"
                } catch (e: Exception) {
                    //e.printStackTrace()
                    writeResult = "Ошибка ${e.message}"
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close()
                        } catch (e: IOException) {
                            //e.printStackTrace()
                            writeResult = "Ошибка ${e.message}"
                        }
                    }
                    if (writeResult.isNotBlank()) {
                        Toast.makeText(event.context, writeResult, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    data class AdminGameListState(
        val result: UiState<List<GameModel>> = UiState.Default
    )

    /*val games = listOf(
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
    )*/
}