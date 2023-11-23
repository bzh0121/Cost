package cc.bzzzh.woodenfish.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.routeconfig.WoodenFishRouteConfig
import cc.bzzzh.woodenfish.view.WoodenFishScreen
import com.google.accompanist.navigation.animation.composable

/**
 * 木鱼
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.woodenFishNavHost(navController: NavController) {
    composable(WoodenFishRouteConfig.WOODEN_FISH_PAGE) {
        WoodenFishScreen()
    }
}