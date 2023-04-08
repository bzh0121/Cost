package cc.bzzzh.base.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 账单实体
 */
@Entity(tableName = "bill")
@Parcelize
data class Bill(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var tag: String? = null,
    var count: Double,
    var time: Long,
    val sortId: Int     //关联的sortId
) : Parcelable