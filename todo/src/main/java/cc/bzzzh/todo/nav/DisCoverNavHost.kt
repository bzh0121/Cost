package cc.bzzzh.todo.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cc.bzzzh.base.routeconfig.DiscoverRouteConfig
import cc.bzzzh.todo.view.DiscoverScreen

fun NavGraphBuilder.disCoverNavHost(navController: NavController) {
    composable(DiscoverRouteConfig.DISCOVER_PAGE) {
        DiscoverScreen()
    }
}