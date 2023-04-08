package cc.bzzzh.base.data.db.dao

import androidx.room.*
import cc.bzzzh.base.data.model.BillSort
import kotlinx.coroutines.flow.Flow

@Dao
interface BillSortDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(vararg billSort: BillSort)

    @Update
    suspend fun update(vararg billSort: BillSort)

    @Delete
    suspend fun del(billSort: BillSort)

    @Query("DELETE FROM bill_sort")
    suspend fun clearAll()

    @Query("SELECT count(1) FROM bill_sort")
    suspend fun getCount() : Int

    @Query("SELECT * FROM bill_sort")
    fun getAll() : Flow<List<BillSort>>

    @Query("SELECT * FROM bill_sort WHERE isHide = 0")
    fun getAllDisplay() : Flow<List<BillSort>>

    @Query("SELECT * FROM bill_sort WHERE isHide = 0 and type = 0")
    fun getAllDisplayCost() : Flow<List<BillSort>>

    @Query("SELECT * FROM bill_sort WHERE isHide = 0 and type = 1")
    fun getAllDisplayPayoff() : Flow<List<BillSort>>

    @Query("SELECT * FROM bill_sort WHERE isHide = 1")
    fun getAllNotDisplay() : Flow<List<BillSort>>


}