package cc.bzzzh.todo.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cc.bzzzh.base.routeconfig.ChartRouteConfig
import cc.bzzzh.todo.view.ChartScreen


fun NavGraphBuilder.chartNavHost(navController: NavController) {
    composable(ChartRouteConfig.CHART_PAGE) {
        ChartScreen()
    }
}