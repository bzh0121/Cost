package cc.bzzzh.base.intent

import androidx.navigation.NavController

open class BaseAction(protected val navController: NavController) {

    /**
     * 返回上一页的闭包方法
     */
    val backLastScreen: () -> Unit = {
        navController.popBackStack()
    }

    /**
     * 返回上一页的方法
     */
    fun back() {
        navController.popBackStack()
    }

    /**
     * 打开指定路由的方法
     */
    fun openLink(route: String) {
        navController.navigate(route)
    }

}