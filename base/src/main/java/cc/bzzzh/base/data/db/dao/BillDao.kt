package cc.bzzzh.base.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillWithSort
import cc.bzzzh.base.data.model.DailyCountTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(vararg bills: Bill)

    @Delete
    suspend fun del(bill: Bill)

    @Query("DELETE FROM bill WHERE id = :id")
    suspend fun del(id: Int)

    @Query("DELETE FROM bill")
    suspend fun clearAll()

    @Transaction
    @Query("SELECT * FROM bill ORDER BY time desc, id desc")
    fun getAll() : PagingSource<Int, BillWithSort>

//    @Transaction
//    @Query("SELECT * FROM bill Group by time")
//    fun getAllGroupByDay() : PagingSource<Int, DailyBill>

    @Query("SELECT time, sum(count) AS sumCount FROM bill GROUP BY time")
    fun getDailySumCount(): Flow<List<DailyCountTuple>>

    @Query("SELECT sum(count) FROM bill " +
            "INNER JOIN bill_sort ON bill_sort.id = bill.sortId " +
            "WHERE time >= :startTime AND time <= :endTime " +
            "AND bill_sort.type = :sortType ")
    fun queryCount(startTime: Long, endTime: Long, sortType: Int): Flow<Double?>
}