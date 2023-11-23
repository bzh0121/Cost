package cc.bzzzh.base.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.getSystemService

/**
 * 震动
 */
object VibratorUtil {

    /**
     * 触发震动
     */
    fun startVibrator(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            /*val vibratorManager = context.getSystemService(ComponentActivity.VIBRATOR_MANAGER_SERVICE)
                    as VibratorManager*/
            //core-ktx提供方法
            val vibratorManager = context.getSystemService<VibratorManager>()
            vibratorManager?.getVibrator(0)
        } else {
            //context.getSystemService(ComponentActivity.VIBRATOR_SERVICE) as Vibrator
            context.getSystemService<Vibrator>()
        }
        vibrator?.run {
            if (hasVibrator()) {
                vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }
    }
}