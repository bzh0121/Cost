package cc.bzzzh.base.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * 带分类展示的账单实体
 */
data class BillWithSort(
    @Embedded val bill: Bill,

    @Relation(
        parentColumn = "sortId",
        entityColumn = "id"
    )
    val sort: BillSort,
) /*{

    @Ignore
    val headCount: Int? = null

    @Ignore
    val headDate: String? = null
}*/

data class DailyCountTuple(
    var time: Long,
    val sumCount: Double
)