package cc.bzzzh.add.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillSort
import cc.bzzzh.base.data.repo.BillRepo
import cc.bzzzh.base.routeconfig.AddRouteParams
import cc.bzzzh.base.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for AddScreen
 */
data class AddBillUiState(
    val isLoading: Boolean = false,     //正在加载
    val isExited: Boolean = false,      //退出界面
    val userMessage: String? = null,    //提示用户

    val isCost: Boolean = true,          //是否是花费
    val chosenSort: BillSort? = null,    //选中的账单类型
    val date: String,                    //选中的日期
)


/**
 * 添加账单
 */
@HiltViewModel
class AddBillVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val billRepo: BillRepo
) : ViewModel() {

    private val preDate: String? = savedStateHandle[AddRouteParams.Date]

    private val _uiState = MutableStateFlow(
        AddBillUiState(date = preDate ?: DateUtils.getTodayDate()))
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("bzzzh", "AddBillVM: init() ")
    }

    override fun onCleared() {
        Log.d("bzzzh", "AddBillVM: onCleared() ")
        clearState()
        super.onCleared()
    }

    /**
     * 设定是否是花费
     */
    fun setIsCost(isCost: Boolean) {
        _uiState.update { it.copy(
            isCost = isCost
        ) }
    }

    /**
     * 设置选中的账单
     */
    fun setChosenSort(chosenSort: BillSort?) {
        _uiState.update { it.copy(
            chosenSort = chosenSort
        ) }
    }

    /**
     * 设置日期
     */
    fun setDate(date: String) {
        _uiState.update { it.copy(
            date = date
        ) }
    }


    /**
     * 获取支出分类
     */
    fun getAllCostBillSort() : Flow<List<BillSort>> {
        return billRepo.getAllDisplayCostBillSort()
    }

    /**
     * 获取收入分类
     */
    fun getAllPayoffBillSort() : Flow<List<BillSort>> {
        return billRepo.getAllDisplayPayoffBillSort()
    }

    /**
     * 保存账单
     */
    fun saveBill(count: Double, remark: String, time: Long) {
        _uiState.update { it.copy(isLoading = true,) }
        if (_uiState.value.chosenSort != null) {
            viewModelScope.launch {
                billRepo.saveBill(
                    bill = Bill(
                        count = count,
                        name = remark.ifEmpty { _uiState.value.chosenSort?.name ?: "" },
                        time = time,
                        sortId = _uiState.value.chosenSort?.id ?: 0
                    )
                )
                _uiState.update { it.copy(
                    isLoading = false,
                    isExited = true,
                    userMessage = null
                ) }
            }
        } else {
            //处理异常
            _uiState.update { it.copy(
                isLoading = false,
                isExited = false,
                userMessage = "保存失败"
            ) }
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
     * 退出界面
     */
    fun exit() {
        _uiState.update {
            it.copy(isExited = true)
        }
    }

    /**
     * 退出时清理状态
     */
    private fun clearState() {
        _uiState.update {
            it.copy(
                isLoading = false,
                userMessage = null,
                isExited = false,
            )
        }
    }
}