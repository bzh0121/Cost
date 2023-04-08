package cc.bzzzh.cost.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import cc.bzzzh.cost.CostNavGraph
import cc.bzzzh.cost.view.*
import cc.bzzzh.cost.view.bottom.Screen
import cc.bzzzh.cost.view.bottom.bottomScreenItems
import cc.bzzzh.cost.view.bottom.fullScreens
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    //底部导航栏的集合
    val items = bottomScreenItems

    //导航容器
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //是否展示底部导航栏
    var showBottomBar by remember { mutableStateOf(true) }
    showBottomBar =currentDestination?.route?.split("?")?.get(0) !in fullScreens

    //界面脚手架
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController, currentDestination, items)
            }
        }
    ) { innerPadding ->
        //所有页面容器
        CostNavGraph(navController, innerPadding)
    }
}


/**
 * 底部导航栏
 */
@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<Screen>,
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        //弹出到图形的开始目的地
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //重新选择同一项目时，避免同一目标的多个副本
                        launchSingleTop = true
                        // 重新选择以前选定的项目时恢复状态
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        imageVector = ImageVector.vectorResource(id = screen.resourceIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screen.iconSize)
                    )
                },
                label = {
                    if (screen.resourceId != null) {
                        Text(
                            text = stringResource(screen.resourceId)
                        )
                    } else {
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                },
            )
        }
    }
}