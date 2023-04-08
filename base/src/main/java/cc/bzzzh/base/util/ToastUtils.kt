package cc.bzzzh.base.util

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("StaticFieldLeak")
object ToastUtils {

    @Volatile private var toast: Toast? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @SuppressLint("StaticFieldLeak")
    private var context: Application? = null

    fun initContext(context: Application) {
        ToastUtils.context = context
    }

    fun show(text: String, duration:Int = Toast.LENGTH_SHORT) {
        context?.let {
            scope.launch {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    val toast: Toast = Toast.makeText(context, text, duration)
                    toast.show()
                } else {
                    toast?.cancel()
                    toast = null

                    toast = Toast.makeText(context, text, duration)
                    toast?.show()
                }
            }
        }
    }

    fun show(textRes: Int, duration:Int = Toast.LENGTH_SHORT) {
        context?.let {
            scope.launch {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    val toast: Toast = Toast.makeText(context, textRes, duration)
                    toast.show()
                } else {
                    toast?.cancel()
                    toast = null

                    toast = Toast.makeText(context, textRes, duration)
                    toast?.show()
                }
            }
        }
    }
}