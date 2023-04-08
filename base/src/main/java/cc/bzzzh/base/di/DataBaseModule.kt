package cc.bzzzh.base.di

import android.content.Context
import androidx.room.Room
import cc.bzzzh.base.config.constant.PathConstant
import cc.bzzzh.base.data.db.BillDbManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

/**
 * 注册一个全局唯一的billDbManager
 */
@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun getBillDbTaskManager(@ApplicationContext context: Context) : BillDbManager {
        val dir = File(PathConstant.DB_PATH)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return  Room.databaseBuilder(
            context.applicationContext,
            BillDbManager::class.java,
            PathConstant.DB_PATH + File.separator + PathConstant.Bill_DB_FILENAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun getBillDao(db: BillDbManager) = db.getBillDao()

    @Provides
    fun getBillSortDao(db: BillDbManager) = db.getBillSortDao()


}