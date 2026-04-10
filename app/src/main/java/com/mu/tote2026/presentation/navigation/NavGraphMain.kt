package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.Destinations.RatingDestination
import com.mu.tote2026.presentation.navigation.destination.admin.common.adminFinish
import com.mu.tote2026.presentation.navigation.destination.admin.common.adminPrizeFund
import com.mu.tote2026.presentation.navigation.destination.admin.email.adminEmail
import com.mu.tote2026.presentation.navigation.destination.admin.email.adminEmailList
import com.mu.tote2026.presentation.navigation.destination.admin.email.navigateToAdminEmail
import com.mu.tote2026.presentation.navigation.destination.admin.email.navigateToAdminEmailList
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.adminGambler
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.adminGamblerList
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.navigateToAdminGambler
import com.mu.tote2026.presentation.navigation.destination.admin.gambler.navigateToAdminGamblerList
import com.mu.tote2026.presentation.navigation.destination.admin.game.adminGameList
import com.mu.tote2026.presentation.navigation.destination.admin.game.navigateToAdminGames
import com.mu.tote2026.presentation.navigation.destination.admin.group.adminGroup
import com.mu.tote2026.presentation.navigation.destination.admin.group.adminGroupList
import com.mu.tote2026.presentation.navigation.destination.admin.group.navigateToAdminGroup
import com.mu.tote2026.presentation.navigation.destination.admin.group.navigateToAdminGroupList
import com.mu.tote2026.presentation.navigation.destination.admin.main.adminMain
import com.mu.tote2026.presentation.navigation.destination.admin.main.navigateToAdminMain
import com.mu.tote2026.presentation.navigation.destination.admin.stake.adminStakeList
import com.mu.tote2026.presentation.navigation.destination.admin.team.adminTeam
import com.mu.tote2026.presentation.navigation.destination.admin.team.adminTeamList
import com.mu.tote2026.presentation.navigation.destination.admin.team.navigateToAdminTeam
import com.mu.tote2026.presentation.navigation.destination.admin.team.navigateToAdminTeamList
import com.mu.tote2026.presentation.navigation.destination.game.game
import com.mu.tote2026.presentation.navigation.destination.game.games
import com.mu.tote2026.presentation.navigation.destination.game.groupGames
import com.mu.tote2026.presentation.navigation.destination.game.navigateToGame
import com.mu.tote2026.presentation.navigation.destination.game.navigateToGames
import com.mu.tote2026.presentation.navigation.destination.game.navigateToGroupGames
import com.mu.tote2026.presentation.navigation.destination.prognosis.prognosis
import com.mu.tote2026.presentation.navigation.destination.rating.gamblerPhoto
import com.mu.tote2026.presentation.navigation.destination.rating.navigateToGamblerPhoto
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
        rating(
            toGamblerPhoto = { args ->
                navMainController.navigateToGamblerPhoto(args)
            }
        )
        gamblerPhoto()
        stakes(
            toStakeEdit = { args ->
                navMainController.navigateToStake(args)
            }
        )
        stake(
            toStakeList = { navMainController.navigateToStakeList() }
        )
        prognosis()
        games(
            toGroupGameList = { args ->
                navMainController.navigateToGroupGames(args)
            },
            toGameEdit = { args ->
                navMainController.navigateToGame(args)
            }
        )
        groupGames(
            toGameEdit = { args ->
                navMainController.navigateToGame(args)
            }
        )
        game(
            toGroupGamesList = { args ->
                navMainController.navigateToGroupGames(args)
            },
            toGameList = { navMainController.navigateToGames() },
            toAdminGames = { navMainController.navigateToAdminGames() }
        )

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
        adminTeamList(
            toTeamEdit = { id ->
                navMainController.navigateToAdminTeam(id)
            }
        )
        adminTeam(
            toAdminTeamList = { navMainController.navigateToAdminTeamList() }
        )
        adminGamblerList(
            toGamblerEdit = { id ->
                navMainController.navigateToAdminGambler(id)
            }
        )
        adminGambler(
            toAdminGamblerList = { navMainController.navigateToAdminGamblerList() }
        )
        adminGameList(
            toGameEdit = { args ->
                navMainController.navigateToGame(args)
            }
        )
        adminStakeList()
        adminPrizeFund()
        adminFinish(
            toAdmin = { navMainController.navigateToAdminMain() }
        )
    }
}