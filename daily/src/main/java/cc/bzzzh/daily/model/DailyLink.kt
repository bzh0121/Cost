package cc.bzzzh.daily.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cc.bzzzh.base.routeconfig.WebViewRouteConfig
import cc.bzzzh.base.routeconfig.WoodenFishRouteConfig
import cc.bzzzh.daily.R

/**
 * 首页里头部的导航类
 */
sealed class DailyLink(
    val route: String,                      //导航路由
    @StringRes val resourceId: Int,         //文字
    @DrawableRes val resourceIcon: Int,     //图标
) {
    object WoodenFishLink: DailyLink(WoodenFishRouteConfig.WOODEN_FISH_PAGE, R.string.gongde, R.drawable.ic_fish)

    object BillLink: DailyLink(WebViewRouteConfig.WEB_VIEW_PAGE, R.string.bill, R.drawable.bottom_items)
}

/**
 * 用来渲染主页快捷入口的数据集合
 */
val dailyLinks = listOf(
    DailyLink.WoodenFishLink,
    DailyLink.BillLink,
    DailyLink.BillLink,
    DailyLink.BillLink,
    DailyLink.BillLink,
)