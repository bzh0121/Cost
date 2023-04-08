package cc.bzzzh.cost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cc.bzzzh.add.nav.addNavHost
import cc.bzzzh.add.nav.dateSelNavHost
import cc.bzzzh.cost.view.bottom.Screen
import cc.bzzzh.daily.nav.dailyNavHost
import cc.bzzzh.todo.nav.chartNavHost
import cc.bzzzh.todo.nav.disCoverNavHost
import cc.bzzzh.todo.nav.personNavHost

/**
 * 导航图总览
 */
@Composable
fun CostNavGraph(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues()
) {

    NavHost(
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
    }
}