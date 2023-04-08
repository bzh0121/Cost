package cc.bzzzh.base.util
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 日期工具类
 */
object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * 判断日期字符串是否为当天
     * @param dateString String
     * @return Boolean
     */
    fun isToday(dateString: String): Boolean {
        if (dateString.isNullOrEmpty()) {
            return false
        }
        val current = LocalDateTime.now()
        val formatted = current.format(formatter)
        return dateString.substring(0, 10) == formatted
    }

    fun getTodayDate(): String {
        val current = LocalDateTime.now()
        return current.format(formatter)
    }

    /**
     * 获取当前年份
     * @return Int
     */
    fun getCurrentYear(): Int {
        return LocalDateTime.now().year
    }

    /**
     * 获取当前月份
     * @return Int
     */
    fun getCurrentMonth(): Int {
        return LocalDateTime.now().month.value
    }

    /**
     * 判断当前日期是星期几
     */
    fun getWeekOfDate(dt: Date): Int {
        return LocalDateTime.now().dayOfWeek.value
    }

    /**
     * 判断当前日期是星期几
     */
    fun getWeekOfDate(dtStr: String): Int {
        val dt = LocalDate.parse(dtStr, formatter)
        return dt.dayOfWeek.value
    }

    fun getDayOfMonth(dtStr: String): Int {
        val dt = LocalDate.parse(dtStr, formatter)
        return dt.lengthOfMonth()
    }

    /**
     * 当月份为1位时返回两位
     * @param month Int
     * @return String
     */
    fun getMonthValue(month: Int): String {
        return if (month < 10) {
            "0$month"
        } else {
            "$month"
        }
    }

    /**
     * 判断时间1是否早于时间2
     * @param time1Str String
     * @param time2Str String
     */
    fun compareTime(time1Str: String, time2Str: String): Boolean {
        val time1 = LocalDate.parse(time1Str, formatter)
        val time2 = LocalDate.parse(time2Str, formatter)
        return time1.isBefore(time2)
    }

    /**
     * 查询时间是否在某个区间内
     * @param timeStr String    要查询的时间
     * @param limit List<String>   区间边界
     * @return DatePosition
     */
    fun checkDataIsInTime(timeStr: String, limit: List<String>): DatePosition {
        when (limit.size) {
            1 -> {
                return if (timeStr == limit[0]) {
                    DatePosition.SINGLE_1
                } else {
                    DatePosition.OTHER
                }
            }
            2 -> {
                if (limit[0] == limit[1]) {
                    return if (timeStr == limit[0]) {
                        DatePosition.SINGLE_2
                    } else {
                        DatePosition.OTHER
                    }
                } else {
                    val time = LocalDate.parse(timeStr, formatter)
                    val start: LocalDate
                    val end: LocalDate
                    if (compareTime(limit[0], limit[1])) {
                        start = LocalDate.parse(limit[0], formatter)
                        end = LocalDate.parse(limit[1], formatter)
                    } else {
                        start = LocalDate.parse(limit[1], formatter)
                        end = LocalDate.parse(limit[0], formatter)
                    }
                    return if (start.isBefore(time) && end.isAfter(time)) {
                        DatePosition.MIDDLE
                    } else if (start.isEqual(time)) {
                        DatePosition.START
                    } else if (end.isEqual(time)) {
                        DatePosition.END
                    } else {
                        DatePosition.OTHER
                    }
                }
            }
            else -> {
                return DatePosition.OTHER
            }
        }
    }
}

enum class DatePosition {
    //开始节点
    START,//开始节点
    MIDDLE,//中间节点
    END,//结尾节点
    SINGLE_1,//单边界
    SINGLE_2,//双边界，左右边界同一天
    OTHER//未选中
}