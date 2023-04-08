package cc.bzzzh.cost.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cc.bzzzh.base.config.constant.PathConstant
import cc.bzzzh.base.util.SpUtils
import cc.bzzzh.base.util.ToastUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CostApplication: Application() {

    companion object {
        //仅可以静态持有ApplicationContext
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initCtx()
    }

    private fun initCtx() {
        SpUtils.initContext(this)
        ToastUtils.initContext(this)
        PathConstant.initContext(this)
    }
}