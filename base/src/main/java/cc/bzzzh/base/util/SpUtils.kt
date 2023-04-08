package cc.bzzzh.base.util

import android.annotation.SuppressLint
import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * 腾讯的key-value 轻量数据存储管理类
 */
@SuppressLint("StaticFieldLeak")
object SpUtils {

    private val kv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

//    init {
//        MMKV.initialize(BaseApplication.context)
//    }

    fun initContext(context: Application) {
        MMKV.initialize(context.applicationContext)
    }

    fun put(key: String, value: Any?) {
        when (value) {
            is Boolean -> kv.putBoolean(key, value)
            is ByteArray -> kv.putBytes(key, value)
            is Float -> kv.putFloat(key, value)
            is Int -> kv.putInt(key, value)
            is Long -> kv.putLong(key, value)
            is String -> kv.putString(key, value)
            else -> error("${value?.javaClass?.simpleName} Not Supported By SpUtils")
        }
    }

    fun getBoolean(key: String, defValue: Boolean = false) = kv.getBoolean(key, defValue)

    fun getBytes(key: String, defValue: ByteArray? = null) = kv.getBytes(key, defValue)

    fun getFloat(key: String, defValue: Float = 0f) = kv.getFloat(key, defValue)

    fun getInt(key: String, defValue: Int = 0) = kv.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0L) = kv.getLong(key, defValue)

    fun getString(key: String, defValue: String? = null) = kv.getString(key, defValue)

    fun remove(key: String) = kv.remove(key)

    fun removeValue(key: String) = kv.removeValueForKey(key)
}