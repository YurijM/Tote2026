package com.mu.tote2026.presentation.screen.main

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.presentation.utils.Errors.ERROR_PROFILE_IS_EMPTY
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    toAuth: () -> Unit,
    toProfile: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("MainScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
            }

            is UiState.Error -> {
                isLoading = false
                error = result.error
                when (error) {
                    ERROR_PROFILE_IS_EMPTY -> toProfile()
                    else -> toAuth()
                }

            }

            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = viewModel.gambler.toString(),
            textAlign = TextAlign.Center
        )
        if (error.isNotBlank()) {
            val context = LocalContext.current
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

    }
}