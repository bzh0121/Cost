package cc.bzzzh.add.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import cc.bzzzh.add.intent.DateSelAction
import cc.bzzzh.add.view.DateSelScreen
import cc.bzzzh.base.routeconfig.AddRouteConfig
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dateSelNavHost(navController: NavController) {
    composable(AddRouteConfig.DATE_SEL_PAGE) {
        val action = DateSelAction(navController)
        DateSelScreen(action)
    }


//    //???
//    navigation(startDestination = AddRouteConfig.Date_Sel_PAGE, route = AddRouteConfig.ADD_ROOT) {
//        composable(AddRouteConfig.DATE_SEL_PAGE) {
//            val action = DateSelAction(navController)
//            DateSelScreen(action)
//        }
//    }
}