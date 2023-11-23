package cc.bzzzh.todo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.routeconfig.DiscoverRouteConfig
import cc.bzzzh.todo.view.DiscoverScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.disCoverNavHost(navController: NavController) {
    composable(DiscoverRouteConfig.DISCOVER_PAGE) {
        DiscoverScreen()
    }
}