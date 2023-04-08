package cc.bzzzh.add.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cc.bzzzh.add.intent.DateSelAction
import cc.bzzzh.add.view.DateSelScreen
import cc.bzzzh.base.routeconfig.AddRouteConfig

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