package cc.bzzzh.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.bzzzh.base.util.DatePosition
import cc.bzzzh.base.util.DateUtils
import java.time.LocalDateTime
import java.time.Month

/**
 * 可以选择日期范围的日历控件
 */

@Composable
fun CalendarCrossComponent(
    startDate: String = DateUtils.getTodayDate(),
    endDate: String = DateUtils.getTodayDate(),
    dateSelectedBack: (String, String) -> Unit
) {
    //当前年份
    var cYear by remember {
        mutableStateOf(LocalDateTime.now().year)
    }
    //当前月
    var cMonth by remember {
        mutableStateOf(LocalDateTime.now().month)
    }

    //选中的日期
    val selectedDate = remember {
        mutableStateListOf(startDate, endDate)
    }

    MonthComponent(
        year = cYear,
        month = cMonth,
        selectDay = selectedDate,
        dayClick = {
            if (selectedDate.size == 2) {
                selectedDate.clear()
            }
            selectedDate.add(it)
            if (selectedDate.size == 2) {
                if (DateUtils.compareTime(selectedDate[0], selectedDate[1])) {
                    dateSelectedBack.invoke(selectedDate[0], selectedDate[1])
                } else {
                    dateSelectedBack.invoke(selectedDate[1], selectedDate[0])
                }
            }
        }, preMonth = {
            if (cMonth.value == 1) {
                cYear = cYear.minus(1)
                cMonth = Month.DECEMBER
            } else {
                cMonth = cMonth.minus(1)
            }
        }, nexMonth = {
            if (cMonth.value == 12) {
                cYear = cYear.plus(1)
                cMonth = Month.JANUARY
            } else {
                cMonth = cMonth.plus(1)
            }
        }
    )
}

/**
 * 月份组件
 * @param year Int  当前年份
 * @param month Month   当前月份
 * @param selectDay List<String>    已选中的日期
 * @param preMonth Function0<Unit>  上一个月点击事件
 * @param nexMonth Function0<Unit>  下一个月点击事件
 * @param dayClick Function1<String, Unit>
 */
@Composable
fun MonthComponent(
    year: Int,
    month: Month,
    selectDay: List<String>,
    preMonth: () -> Unit,
    nexMonth: () -> Unit,
    dayClick: (String) -> Unit
) {
    val textColor = Color.Black

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier
                    //.rotate(180f)
                    .clickable {
                        preMonth.invoke()
                    }
            )
            Text(
                text = "$year-${DateUtils.getMonthValue(month.value)}",
                color = textColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.clickable {
                    nexMonth.invoke()
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        //星期展示
        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "周一", color = textColor, fontSize = 14.sp)
            Text(text = "周二", color = textColor, fontSize = 14.sp)
            Text(text = "周三", color = textColor, fontSize = 14.sp)
            Text(text = "周四", color = textColor, fontSize = 14.sp)
            Text(text = "周五", color = textColor, fontSize = 14.sp)
            Text(text = "周六", color = textColor, fontSize = 14.sp)
            Text(text = "周日", color = textColor, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            items(
                DateUtils.getWeekOfDate(
                    "$year-${DateUtils.getMonthValue(month.value)}-01") - 1
            ) {
                Text(text = "")
            }
            items(
                DateUtils.getDayOfMonth(
                    "$year-${DateUtils.getMonthValue(month.value)}-01")
            ) { index ->
                Day(
                    day = index + 1,
                    DateUtils.checkDataIsInTime(
                        "$year-${DateUtils.getMonthValue(month.value)}-${
                            DateUtils.getMonthValue(index + 1)
                        }", selectDay
                    ),
                    dayClick = {
                        dayClick.invoke(
                            "$year-${DateUtils.getMonthValue(month.value)}-${
                                DateUtils.getMonthValue(
                                    it
                                )
                            }"
                        )
                    })
            }
        }
    }
}


//建一个函数，day就是第几天，selectDay用来判断是否是被点击的那天，
// 然后抛出去一个lambda，处理点击事件，lambda传入的day就是用来说明是第几天。
@Composable
fun Day(day: Int, position: DatePosition, dayClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart =
                    if (position == DatePosition.START
                        || position == DatePosition.SINGLE_1
                        || position == DatePosition.SINGLE_2
                    )
                        10.dp
                    else
                        0.dp,
                    bottomStart =
                    if (position == DatePosition.START
                        || position == DatePosition.SINGLE_1
                        || position == DatePosition.SINGLE_2
                    )
                        10.dp
                    else
                        0.dp,
                    topEnd =
                    if (position == DatePosition.END
                        || position == DatePosition.SINGLE_1
                        || position == DatePosition.SINGLE_2
                    )
                        10.dp
                    else
                        0.dp,
                    bottomEnd =
                    if (position == DatePosition.END
                        || position == DatePosition.SINGLE_1
                        || position == DatePosition.SINGLE_2
                    )
                        10.dp
                    else
                        0.dp
                )
            )
            .background(
                when (position) {
                    DatePosition.OTHER -> Color.Transparent
                    DatePosition.SINGLE_1 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.primary
                }
            )
            .clickable {
                dayClick(day)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }

}