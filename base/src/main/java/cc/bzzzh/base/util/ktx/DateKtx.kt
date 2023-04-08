package cc.bzzzh.base.util.ktx

import cc.bzzzh.base.config.constant.FormatConstant
import java.text.ParseException

fun Long.getDayTime() : Long {
    return try {
        FormatConstant.sdf4.parse(
            FormatConstant.sdf4.format(this)
        )?.time ?: this
    } catch (e: ParseException) {
        this
    }
}

fun Long.getDayStr() : String? {
    return try {
        FormatConstant.sdf4.format(this)
    } catch (e: ParseException) {
        null
    }
}

fun Long.getWeek() : String? {
    return try {
        FormatConstant.week.format(this)
    } catch (e: ParseException) {
        null
    }
}



fun String.getDayTime() : Long? {
    return try {
        FormatConstant.sdf4.parse(this)?.time
    } catch (e: ParseException) {
        null
    }
}



