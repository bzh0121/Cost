package cc.bzzzh.todo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.base.routeconfig.PersonRouteConfig
import cc.bzzzh.todo.view.PersonScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.personNavHost(navController: NavController) {
    composable(PersonRouteConfig.PERSON_PAGE) {
        PersonScreen()
    }
}