package cc.bzzzh.todo.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cc.bzzzh.base.routeconfig.PersonRouteConfig
import cc.bzzzh.todo.view.PersonScreen

fun NavGraphBuilder.personNavHost(navController: NavController) {
    composable(PersonRouteConfig.PERSON_PAGE) {
        PersonScreen()
    }
}