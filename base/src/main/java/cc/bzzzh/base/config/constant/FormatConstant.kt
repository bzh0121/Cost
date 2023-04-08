package cc.bzzzh.base.config.constant

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatConstant {
    val df0 = DecimalFormat("#")
    val df1 = DecimalFormat("#.#")
    val df2 = DecimalFormat("#.##")
    val df3 = DecimalFormat("#.###")
    val df4 = DecimalFormat("#.####")
    val df5 = DecimalFormat("#.#####")
    val df6 = DecimalFormat("#.######")
    val df7 = DecimalFormat("#.#######")

    val df2_00 = DecimalFormat("0.00")

    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    val sdf2: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
    val sdf3: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA)
    val sdf4: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

    val week: SimpleDateFormat = SimpleDateFormat("EEEE", Locale.CHINA)





}