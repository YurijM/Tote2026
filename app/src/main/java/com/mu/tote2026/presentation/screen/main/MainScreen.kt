package com.mu.tote2026.presentation.screen.main

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.ApplicationBar
import com.mu.tote2026.presentation.components.BottomNav
import com.mu.tote2026.presentation.navigation.NavGraphMain
import com.mu.tote2026.presentation.utils.Errors.ERROR_PROFILE_IS_EMPTY
import com.mu.tote2026.presentation.utils.Route.ADMIN_MAIN_SCREEN
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    toAuth: () -> Unit,
    toProfile: () -> Unit
) {
    val navMainController = rememberNavController()
    val navBackStackEntry by navMainController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current as Activity

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

    Scaffold(
        bottomBar = {
            if (viewModel.gambler.rate > 0) {
                BottomNav(
                    currentRoute,
                    viewModel.currentYear
                ) { route ->
                    navMainController.navigate(route)
                }
            }
        },
        topBar = {
            ApplicationBar(
                photoUrl = viewModel.gambler.photoUrl,
                isAdmin = viewModel.gambler.isAdmin,
                onImageClick = { toProfile() },
                onAdminClick = { navMainController.navigate(ADMIN_MAIN_SCREEN) },
                onSignOut = {
                    viewModel.signOut()
                    context.finish()
                    //activity?.finish()
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            contentColor = MaterialTheme.colorScheme.primary,
            color = MaterialTheme.colorScheme.surface
        ) {
            NavGraphMain(navMainController = navMainController)

            if (isLoading) {
                AppProgressBar()
            }
        }
    }
}
