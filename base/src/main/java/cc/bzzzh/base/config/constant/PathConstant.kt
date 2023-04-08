package cc.bzzzh.base.config.constant

import android.app.Application
import java.io.File

object PathConstant {
    const val DB_DIR_NAME = "database"
    const val Bill_DB_FILENAME = "bill.db"

    var ROOT_PATH: String? = ""
    var APP_PATH: String = ""
    var DB_PATH : String = ""



    /**
     * 注入上下文
     */
    fun initContext(context: Application) {
        ROOT_PATH = context.getExternalFilesDir(null)?.path
        APP_PATH = ROOT_PATH + File.separator + "Cost"
        DB_PATH = APP_PATH + File.separator + DB_DIR_NAME
    }
}