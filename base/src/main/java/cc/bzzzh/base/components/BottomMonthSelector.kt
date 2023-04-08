package cc.bzzzh.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cc.bzzzh.cost.ui.theme.DivideLine

/**
 * todo 底部滚动月份选择器
 */
@Composable
fun BottomMonthSelector(preYear: Int, preMonth: Int, onSelect: (Int, Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
           Text(text = "取消")
           Text(text = "选择月份")
           Text(text = "确定")
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.DivideLine))

        Row(

        ) {
            LazyColumn {

            }
        }
    }
}