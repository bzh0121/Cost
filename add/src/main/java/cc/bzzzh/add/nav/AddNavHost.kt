package cc.bzzzh.add.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import cc.bzzzh.add.intent.AddAction
import cc.bzzzh.add.view.AddScreen
import cc.bzzzh.base.routeconfig.AddRouteConfig
import cc.bzzzh.base.routeconfig.AddRouteParams
import cc.bzzzh.base.util.DateUtils
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addNavHost(navController: NavController) {
    composable(
        route = "${AddRouteConfig.ADD_PAGE}?${AddRouteParams.Date}={${AddRouteParams.Date}}",
        arguments = listOf(
            navArgument(AddRouteParams.Date) {
                type = NavType.StringType
                defaultValue = DateUtils.getTodayDate()
                nullable = true
            }
        ),
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
        },
        popEnterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            )
        },
        popExitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
        }
    ) {
        val addAction = AddAction(navController)
        AddScreen(
            onBackClick = addAction.backLastScreen
        )
    }
}