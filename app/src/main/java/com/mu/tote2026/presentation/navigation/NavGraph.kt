package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.Destinations.SplashDestination
import com.mu.tote2026.presentation.navigation.destination.auth.auth
import com.mu.tote2026.presentation.navigation.destination.auth.navigateToAuth
import com.mu.tote2026.presentation.navigation.destination.auth.navigateToSignIn
import com.mu.tote2026.presentation.navigation.destination.auth.navigateToSignUp
import com.mu.tote2026.presentation.navigation.destination.auth.signIn
import com.mu.tote2026.presentation.navigation.destination.auth.signUp
import com.mu.tote2026.presentation.navigation.destination.main.main
import com.mu.tote2026.presentation.navigation.destination.main.navigateToMain
import com.mu.tote2026.presentation.navigation.destination.profile.navigateToProfile
import com.mu.tote2026.presentation.navigation.destination.profile.profile
import com.mu.tote2026.presentation.navigation.destination.splash.splash
import com.mu.tote2026.presentation.navigation.destination.test.test

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination
    ) {
        test()
        splash(
            toAuth = { navController.navigateToAuth() },
            toMain = { navController.navigateToMain() }
        )
        auth(
            toSignUp = { navController.navigateToSignUp() },
            toSignIn = { navController.navigateToSignIn() }
        )
        signUp(
            toProfile = { navController.navigateToProfile() }
            /*toProfile = { email ->
                navController.navigateToProfile(email)
            }*/
        )
        signIn(
            toMain = { navController.navigateToMain() }
        )
        main(
            toAuth = { navController.navigateToAuth() },
            toProfile = { navController.navigateToProfile() }
        )
        profile(
            toMain = { navController.navigateToMain() },
            toAuth = { navController.navigateToAuth() }
        )
    }
}