package cc.bzzzh.cost.factory

import cc.bzzzh.base.data.db.dao.BillSortDao
import cc.bzzzh.base.data.model.BillSort
import cc.bzzzh.cost.R
import javax.inject.Inject
import javax.inject.Singleton


/**
 * 预制数据构建工厂
 */
@Singleton
class BillSortDataPreCreator @Inject constructor(
    private val billSortDao: BillSortDao,
) {

    /**
     * 预制billSort数据
     */
    suspend fun preCreate() {

        val preSortList = listOf(
            BillSort(
                name = "吃的",
                icon = R.drawable.sort_eat
            ),
            BillSort(
                name = "喝的",
                icon = R.drawable.sort_drink
            ),
            BillSort(
                name = "交通",
                icon = R.drawable.sort_taxi,
            ),
            BillSort(
                name = "送礼",
                icon = R.drawable.sort_gift,
            ),
            BillSort(
                name = "发红包",
                icon = R.drawable.sort_send_money,
            ),
            BillSort(
                name = "打麻将",
                icon = R.drawable.sort_majiang,
            )
        )

        preSortList.forEachIndexed { index, billSort ->
            billSort.id = index + 1
        }

        billSortDao.add(*preSortList.toTypedArray())
        billSortDao.update(*preSortList.toTypedArray())
    }
}