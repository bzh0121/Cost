package cc.bzzzh.add.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    val isLoading: Boolean = false,
    val isExited: Boolean = false,
    val userMessage: String? = null,
)


/**
 * 添加账单
 */
@HiltViewModel
class AddBillVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val billRepo: BillRepo
) : ViewModel() {
    var isCost by mutableStateOf(true)   //是否是花费
    var chosenSort by mutableStateOf<BillSort?>(null) //选中的账单类型

    private val preDate: String? = savedStateHandle[AddRouteParams.Date]
    var date by mutableStateOf(preDate ?: DateUtils.getTodayDate())

    private val _uiState = MutableStateFlow(AddBillUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("bzzzh", "AddBillVM: init() ")
    }

    override fun onCleared() {
        Log.d("bzzzh", "AddBillVM: onCleared() ")
        clearState()
        super.onCleared()
    }


    fun getAllCostBillSort() : Flow<List<BillSort>> {
        return billRepo.getAllDisplayCostBillSort()
    }

    fun getAllPayoffBillSort() : Flow<List<BillSort>> {
        return billRepo.getAllDisplayPayoffBillSort()
    }

    fun saveBill(count: Double, remark: String, time: Long) {
        _uiState.update { it.copy(isLoading = true,) }
        if (chosenSort != null) {
            viewModelScope.launch {
                billRepo.saveBill(
                    bill = Bill(
                        count = count,
                        name = remark.ifEmpty { chosenSort?.name ?: "" },
                        time = time,
                        sortId = chosenSort?.id ?: 0
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