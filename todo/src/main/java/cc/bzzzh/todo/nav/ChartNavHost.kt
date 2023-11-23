package cc.bzzzh.todo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.routeconfig.ChartRouteConfig
import cc.bzzzh.todo.view.ChartScreen
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.chartNavHost(navController: NavController) {
    composable(ChartRouteConfig.CHART_PAGE) {
        ChartScreen()
    }
}