package cc.bzzzh.cost.view.bottom

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cc.bzzzh.base.routeconfig.*
import cc.bzzzh.cost.R

/**
 * 主页的底部导航按钮集合
 */
sealed class Screen(
    val route: String,                      //导航路由
    @StringRes val resourceId: Int?,        //文字
    @DrawableRes val resourceIcon: Int,     //图标
    val iconSize: Dp = 25.dp                //图标尺寸
    )
{
    object Items : Screen(DailyRouteConfig.DAILY_PAGE, R.string.bottom_bar_items, R.drawable.bottom_items)
    object Chart : Screen(ChartRouteConfig.CHART_PAGE, R.string.bottom_bar_chart, R.drawable.bottom_chart)
    object Discover : Screen(DiscoverRouteConfig.DISCOVER_PAGE, R.string.bottom_bar_discover, R.drawable.bottom_discover)
    object Person : Screen(PersonRouteConfig.PERSON_PAGE, R.string.bottom_bar_person, R.drawable.bottom_person)

    object Add : Screen(AddRouteConfig.ADD_PAGE, null/*R.string.bottom_bar_add*/,
        R.drawable.add_circle_yellow_bg, 40.dp)
}

/**
 * 底部导航栏集合
 */
val bottomScreenItems = listOf(
    Screen.Items,
    Screen.Chart,
    Screen.Add,
    Screen.Discover,
    Screen.Person,
)

/**
 * 需要全屏显示的路由
 */
val fullScreens = arrayOf(AddRouteConfig.ADD_PAGE, AddRouteConfig.DATE_SEL_PAGE)
