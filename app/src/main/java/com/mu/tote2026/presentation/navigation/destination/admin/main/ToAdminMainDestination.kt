package com.mu.tote2026.presentation.navigation.destination.admin.main

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_MAIN_SCREEN

fun NavController.navigateToAdminMain() {
    navigate(ADMIN_MAIN_SCREEN) {
        popUpTo(ADMIN_MAIN_SCREEN) {
            //inclusive = true
        }
    }}