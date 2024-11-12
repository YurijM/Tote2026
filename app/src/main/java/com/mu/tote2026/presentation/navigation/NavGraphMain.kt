package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.Destinations.RatingDestination
import com.mu.tote2026.presentation.navigation.destination.admin.email.adminEmail
import com.mu.tote2026.presentation.navigation.destination.admin.email.adminEmailList
import com.mu.tote2026.presentation.navigation.destination.admin.email.navigateToAdminEmail
import com.mu.tote2026.presentation.navigation.destination.admin.email.navigateToAdminEmailList
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.adminGambler
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.adminGamblerList
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.navigateToAdminGambler
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.navigateToAdminGamblerList
import com.mu.tote2026.presentation.navigation.destination.admin.game.adminGameList
import com.mu.tote2026.presentation.navigation.destination.admin.group.adminGroup
import com.mu.tote2026.presentation.navigation.destination.admin.group.adminGroupList
import com.mu.tote2026.presentation.navigation.destination.admin.group.navigateToAdminGroup
import com.mu.tote2026.presentation.navigation.destination.admin.group.navigateToAdminGroupList
import com.mu.tote2026.presentation.navigation.destination.admin.main.adminMain
import com.mu.tote2026.presentation.navigation.destination.admin.team.adminTeamList
import com.mu.tote2026.presentation.navigation.destination.game.games
import com.mu.tote2026.presentation.navigation.destination.prognosis.prognosis
import com.mu.tote2026.presentation.navigation.destination.rating.rating
import com.mu.tote2026.presentation.navigation.destination.stake.navigateToStake
import com.mu.tote2026.presentation.navigation.destination.stake.navigateToStakeList
import com.mu.tote2026.presentation.navigation.destination.stake.stake
import com.mu.tote2026.presentation.navigation.destination.stake.stakes

@Composable
fun NavGraphMain(
    navMainController: NavHostController
) {
    NavHost(
        navController = navMainController,
        startDestination = RatingDestination
    ) {
        rating()
        stakes(
            toStakeEdit = { args ->
                navMainController.navigateToStake(args)
            }
        )
        stake(
            toStakeList = { navMainController.navigateToStakeList() }
        )
        prognosis()
        games()

        adminMain(
            toItem = { route ->
                navMainController.navigate(route)
            }
        )
        adminEmailList(
            toEmailEdit = { id ->
                navMainController.navigateToAdminEmail(id)
            }
        )
        adminEmail(
            toAdminEmailList = { navMainController.navigateToAdminEmailList() }
        )
        adminGroupList(
            toGroupEdit = { id ->
                navMainController.navigateToAdminGroup(id)
            }
        )
        adminGroup(
            toAdminGroupList = { navMainController.navigateToAdminGroupList() }
        )
        adminTeamList()
        adminGamblerList(
            toGamblerEdit = { id ->
                navMainController.navigateToAdminGambler(id)
            }
        )
        adminGambler(
            toAdminGamblerList = { navMainController.navigateToAdminGamblerList() }
        )
        adminGameList()
    }
}