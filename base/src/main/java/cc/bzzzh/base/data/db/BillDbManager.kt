package cc.bzzzh.base.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cc.bzzzh.base.data.db.dao.BillDao
import cc.bzzzh.base.data.db.dao.BillSortDao
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillSort

@Database(entities = [Bill::class, BillSort::class],
    version = 1, exportSchema = false)
abstract class BillDbManager : RoomDatabase() {
    abstract fun getBillDao(): BillDao
    abstract fun getBillSortDao(): BillSortDao

   /* companion object {
        @Volatile
        private var instance: BillDbManager? = null

        @Synchronized
        fun getInstance() : BillDbManager {

            val dir = File(PathConstant.DB_PATH)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            //.fallbackToDestructiveMigration
            //在找不到迁移规则时，以破坏性重建数据库，注意这会删除所有数据库表数据。

            return instance ?: Room.databaseBuilder(
                CostApplication.context,
                BillDbManager::class.java,
                PathConstant.DB_PATH + File.separator + PathConstant.Bill_DB_FILENAME)
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
        }
    }


    fun release() {
        instance?.run {
            if (isOpen) {
                close()
            }
        }
        instance = null
    }*/
}