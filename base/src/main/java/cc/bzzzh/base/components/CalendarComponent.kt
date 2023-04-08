package cc.bzzzh.base.components

import androidx.compose.runtime.*
import cc.bzzzh.base.util.DateUtils
import java.time.LocalDateTime
import java.time.Month

/**
 * 单选日历布局
 */
@Composable
fun CalendarComponent(
    preDate: String = DateUtils.getTodayDate(),
    dateSelectedBack: (String) -> Unit
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
        mutableStateListOf(preDate)
    }


    MonthComponent(
        year = cYear,
        month = cMonth,
        selectDay = selectedDate,
        dayClick = {
            selectedDate.clear()
            selectedDate.add(it)
            dateSelectedBack.invoke(it)
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