package cc.bzzzh.add.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cc.bzzzh.add.R
import cc.bzzzh.add.intent.DateSelAction
import cc.bzzzh.base.components.CalendarComponent
import cc.bzzzh.base.util.DateUtils

/**
 * 日历选择页面
 */
@Composable
fun DateSelScreen(
    action: DateSelAction,
) {
    var date by rememberSaveable { mutableStateOf(DateUtils.getTodayDate()) }

    Column {
        Box {
            CalendarComponent(
                preDate = date,
                dateSelectedBack = {
                    date = it
                })

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "取消",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                        .clickable {
                            action.backLastScreen
                        }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(10.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                action.openAddFromDateScreen(date)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.add_circle_yellow_bg),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)

                )
            }
        }

    }
}