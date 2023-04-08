package cc.bzzzh.base.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 账单分类
 */
@Entity(tableName = "bill_sort")
@Parcelize
data class BillSort(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var type: Int = 0,          //支出:0 or 收入:1
    var name: String,
    @DrawableRes
    var icon:  Int,
    var isHide: Boolean = false     //room或将Boolean转成0/1存储
) : Parcelable {

//    @Ignore
//    var isSelected = false

  /*  @IgnoredOnParcel
    var isHide: Boolean
        get() = hide == 1
        set(value) {
            //field = value
            hide = if (value) 1 else 0
        }*/

}