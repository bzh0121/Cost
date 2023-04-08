package cc.bzzzh.base.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cc.bzzzh.base.config.constant.Common
import cc.bzzzh.base.data.db.dao.BillDao
import cc.bzzzh.base.data.db.dao.BillSortDao
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillSort
import cc.bzzzh.base.data.model.BillWithSort
import cc.bzzzh.base.data.model.DailyCountTuple
import cc.bzzzh.base.data.repo.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BillRepo @Inject constructor(
    private val billDao: BillDao,
    private val billSortDao: BillSortDao,
) : BaseRepository() {

    suspend fun saveBill(bill: Bill) {
        billDao.add(bill)
    }

    suspend fun saveBillList(billList: List<Bill>) {
        billDao.add(*billList.toTypedArray())
    }

    suspend fun delBill(bill: Bill) {
        billDao.del(bill)
    }

    suspend fun delBill(billId: Int) {
        billDao.del(billId)
    }

    /**
     * 获取所有账单
     */
    fun getBillList() : Flow<PagingData<BillWithSort>>{
        return Pager(config = PagingConfig(Common.PAGE_SIZE)) {
            billDao.getAll()
        }.flow
    }

    /**
     * 获取按天分组的数目
     */
    fun getDailySumCount(): Flow<List<DailyCountTuple>> =
            billDao.getDailySumCount()


    /**
     * 获取一段时间的统计数目-支出
     */
    fun getCostCount(startTime: Long, endTime: Long): Flow<Double?> {
        return billDao.queryCount(startTime, endTime, 0)
    }

    /**
     * 获取一段时间的统计数目-收入
     */
    fun getPayoffCount(startTime: Long, endTime: Long): Flow<Double?> {
        return billDao.queryCount(startTime, endTime, 1)
    }

    //====================================BillSort=====================================

    suspend fun getSortCount() : Int =
        billSortDao.getCount()


    fun getAllDisplayBillSort() : Flow<List<BillSort>>{
        return billSortDao.getAllDisplay()
    }

    fun getAllDisplayCostBillSort() : Flow<List<BillSort>>{
        return billSortDao.getAllDisplayCost()
    }

    fun getAllDisplayPayoffBillSort() : Flow<List<BillSort>>{
        return billSortDao.getAllDisplayPayoff()
    }


    fun getAllNotDisplayBillSort() : Flow<List<BillSort>>{
        return billSortDao.getAllNotDisplay()
    }


}