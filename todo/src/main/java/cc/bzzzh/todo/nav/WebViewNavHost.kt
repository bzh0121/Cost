package cc.bzzzh.todo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.components.WebViewPage
import cc.bzzzh.base.routeconfig.WebViewRouteConfig
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.webViewNavHost(navController: NavController) {
    composable(WebViewRouteConfig.WEB_VIEW_PAGE) {
        WebViewPage("https://m.zhibo8.cc")
    }
}