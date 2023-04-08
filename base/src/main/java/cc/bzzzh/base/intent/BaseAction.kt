package cc.bzzzh.base.intent

import androidx.navigation.NavController

open class BaseAction(navController: NavController) {

    /**
     * 返回上一页的闭包方法
     */
    val backLastScreen: () -> Unit = {
        navController.popBackStack()
    }

}