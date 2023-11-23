package cc.bzzzh.daily.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.routeconfig.DailyRouteConfig
import cc.bzzzh.daily.intent.DailyAction
import cc.bzzzh.daily.view.DailyBillScreen
import com.google.accompanist.navigation.animation.composable


/**
 * 日常花费模块的导航
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dailyNavHost(navController: NavController) {
    composable(DailyRouteConfig.DAILY_PAGE) {
        val action = DailyAction(navController = navController)
        DailyBillScreen(action = action)
    }
}