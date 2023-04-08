package cc.bzzzh.daily.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cc.bzzzh.base.routeconfig.DailyRouteConfig
import cc.bzzzh.daily.intent.DailyAction
import cc.bzzzh.daily.view.DailyBillScreen
import cc.bzzzh.daily.viewmodel.DailyBillVM


/**
 * 日常花费模块的导航
 */
fun NavGraphBuilder.dailyNavHost(navController: NavController) {
    composable(DailyRouteConfig.DAILY_PAGE) {
        val action = DailyAction(navController = navController)
        val vm = hiltViewModel<DailyBillVM>()
        DailyBillScreen(vm, action)
    }
}