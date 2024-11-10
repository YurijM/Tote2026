package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.auth.AuthScreen
import com.mu.tote2026.presentation.screen.auth.signIn.SignInScreen
import com.mu.tote2026.presentation.screen.auth.signup.SignUpScreen
import com.mu.tote2026.presentation.screen.main.MainScreen
import com.mu.tote2026.presentation.screen.splash.SplashScreen

@Composable
fun NavGraphSafety(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            SplashScreen(
                toAuth = { navController.navigate(AuthRoute) },
                toMain = { navController.navigate(MainRoute) }
            )
        }
        composable<AuthRoute> {
            AuthScreen(
                toSignUp = {
                    navController.navigate(SignUpRoute) {
                        popUpTo(AuthRoute)
                    }
                },
                toSignIn = {
                    navController.navigate(SignInRoute) {
                        popUpTo(AuthRoute)
                    }
                },
            )
        }
        composable<MainRoute> {
            MainScreen(
                toAuth = {},
                toProfile = {}
            )
        }
        composable<SignInRoute> {
            SignInScreen(
                toMain = { navController.navigate(MainRoute) }
            )
        }
        composable<SignUpRoute> {
            SignUpScreen(
                toProfile = { }
            )
        }
    }
}