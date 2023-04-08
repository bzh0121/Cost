package cc.bzzzh.add.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cc.bzzzh.add.intent.AddAction
import cc.bzzzh.add.view.AddScreen
import cc.bzzzh.base.routeconfig.AddRouteConfig
import cc.bzzzh.base.routeconfig.AddRouteParams
import cc.bzzzh.base.util.DateUtils

fun NavGraphBuilder.addNavHost(navController: NavController) {
    composable(
        route = "${AddRouteConfig.ADD_PAGE}?${AddRouteParams.Date}={${AddRouteParams.Date}}",
        arguments = listOf(
            navArgument(AddRouteParams.Date) {
                type = NavType.StringType
                defaultValue = DateUtils.getTodayDate()
                nullable = true
            }
        )
    ) {
        val addAction = AddAction(navController)
        AddScreen(
            onBackClick = addAction.backLastScreen
        )
    }
}