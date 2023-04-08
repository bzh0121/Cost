package cc.bzzzh.daily.intent

import androidx.navigation.NavController
import cc.bzzzh.base.intent.BaseAction
import cc.bzzzh.base.routeconfig.AddRouteConfig

/**
 * app模块的跳转意图
 */
class DailyAction(navController: NavController): BaseAction(navController) {

    //打开搜索界面的闭包方法
    val openSearchScreen = {

    }

    //打开日历窗口
    val openDateScreen = {
        navController.navigate(AddRouteConfig.DATE_SEL_PAGE)
    }
}