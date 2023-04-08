package cc.bzzzh.add.intent

import androidx.navigation.NavController
import cc.bzzzh.base.intent.BaseAction
import cc.bzzzh.base.routeconfig.AddRouteConfig
import cc.bzzzh.base.routeconfig.AddRouteParams

class DateSelAction (navController: NavController) : BaseAction(navController) {

    /**
     * 打开添加账单界面的闭包方法
     */
    val openAddFromDateScreen = { date: String ->
        navController.navigate("${AddRouteConfig.ADD_PAGE}?${AddRouteParams.Date}=${date}")
    }

}