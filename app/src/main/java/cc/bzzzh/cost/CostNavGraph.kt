package cc.bzzzh.cost

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cc.bzzzh.add.nav.addNavHost
import cc.bzzzh.add.nav.dateSelNavHost
import cc.bzzzh.cost.model.Screen
import cc.bzzzh.daily.nav.dailyNavHost
import cc.bzzzh.todo.nav.chartNavHost
import cc.bzzzh.todo.nav.disCoverNavHost
import cc.bzzzh.todo.nav.personNavHost
import cc.bzzzh.todo.nav.webViewNavHost
import cc.bzzzh.woodenfish.nav.woodenFishNavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * 导航图总览
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CostNavGraph(
    navController: NavHostController = rememberAnimatedNavController(),
    innerPadding: PaddingValues = PaddingValues()
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Items.route,
        Modifier.padding(innerPadding)
    ) {
        dailyNavHost(navController)
        chartNavHost(navController)
        disCoverNavHost(navController)
        personNavHost(navController)

        addNavHost(navController)
        dateSelNavHost(navController)

        woodenFishNavHost(navController)
        webViewNavHost(navController)
    }
}