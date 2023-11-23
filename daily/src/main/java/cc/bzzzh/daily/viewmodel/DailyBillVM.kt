package cc.bzzzh.daily.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cc.bzzzh.base.config.constant.FormatConstant
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillWithSort
import cc.bzzzh.base.data.model.DailyCountTuple
import cc.bzzzh.base.data.repo.BillRepo
import cc.bzzzh.base.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * UiState for the DailyBillScreen
 */
data class DailyBillUiState(
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val idBillDeleted : Boolean = false,
    val chosenBill: Bill? = null,       //选中的bill
)

/**
 * 每日账单
 */
@HiltViewModel
class DailyBillVM @Inject constructor(private val billRepo: BillRepo): ViewModel() {
    var year by mutableStateOf(LocalDateTime.now().year)            //选中年份
    var month by mutableStateOf(LocalDateTime.now().month.value)    //选中月份

    private val _uiState = MutableStateFlow(DailyBillUiState())
    val uiState: StateFlow<DailyBillUiState> = _uiState.asStateFlow()

    /**
     * 清理
     */
    override fun onCleared() {
        Log.d("view_model", "onCleared: ")
        super.onCleared()
    }

    /**
     * 获取账单列表
     */
    fun getBillList() : Flow<PagingData<BillWithSort>> {
        return billRepo.getBillList().cachedIn(viewModelScope)
    }

    /**
     * 获取账单日title
     */
    fun getDailySumCount() : Flow<List<DailyCountTuple>> {
        return billRepo.getDailySumCount().map { tuples ->
            tuples.map {
                it.copy(
                    time = it.time,
                    sumCount = FormatConstant.df2_00.format(it.sumCount).toDouble()
                )
            }
        }
    }

    /**
     * 获取月统计数目
     */
    fun getMonthCostCount(year: Int, month: Int) : Flow<Double?> {
        val startTime = "$year-${DateUtils.getMonthValue(month)}-01"
        val endTime = "$year-$month-${DateUtils.getDayOfMonth(startTime)}"

        return billRepo.getCostCount(
            FormatConstant.sdf4.parse(startTime)?.time ?: 0,
            FormatConstant.sdf4.parse(endTime)?.time ?: System.currentTimeMillis())
    }


    /**
     * 获取月统计数目
     */
    fun getMonthCostCount() : Flow<Double?> {
        val startTime = "$year-${DateUtils.getMonthValue(month)}-01"
        val endTime = "$year-$month-${DateUtils.getDayOfMonth(startTime)}"

        return billRepo.getCostCount(
            FormatConstant.sdf4.parse(startTime)?.time ?: 0,
            FormatConstant.sdf4.parse(endTime)?.time ?: System.currentTimeMillis()
        ).map { it?.let { FormatConstant.df2_00.format(it).toDouble() } }
    }

    /**
     * 删除一条账单记录
     */
    fun delBill(bill: Bill) {
        viewModelScope.launch {
            billRepo.delBill(bill)
            _uiState.update {
                it.copy(
                    userMessage = "删除完成！"
                )
            }
        }

    }

    /**
     * 展示消息完成
     */
    fun messageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    /**
     * 设置选择的bill
     */
    fun setChosenBill(chosenBill: Bill?) {
        _uiState.update { it.copy(
            chosenBill = chosenBill
        ) }
    }


    /**
     * 删除选中bill
     */
    fun delChosenBill() {
        viewModelScope.launch {
            _uiState.value.chosenBill?.let {bill ->
                billRepo.delBill(bill)
                _uiState.update {
                    it.copy(
                        userMessage = "删除完成！",
                        chosenBill = null
                    )
                }
            } ?: run {
                _uiState.update {
                    it.copy(
                        userMessage = "删除失败！未选中账单",
                        chosenBill = null
                    )
                }
            }

        }

    }
}