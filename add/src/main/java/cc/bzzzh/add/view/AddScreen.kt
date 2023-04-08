package cc.bzzzh.add.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.bzzzh.add.viewmodel.AddBillVM
import cc.bzzzh.base.components.LoadingView
import cc.bzzzh.base.data.model.BillSort
import cc.bzzzh.base.util.DateUtils
import cc.bzzzh.base.util.ktx.getDayTime
import java.util.*

/**
 * 新增账单
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    vm: AddBillVM = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val uiState by vm.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    uiState.userMessage?.let {
        LaunchedEffect(uiState.userMessage) {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short,
            )
            vm.messageShown()
        }
    }

    LaunchedEffect(uiState.isExited) {
        if (uiState.isExited) {
            onBackClick.invoke()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            //主布局
            AddMainView(vm)

            if (uiState.isLoading) {
                LoadingView()
            }
        }
    }
}

@Composable
fun AddMainView(vm: AddBillVM) {
    Box(
        Modifier.fillMaxSize()
    ) {
        //支出分类集合
        val costBillSortList by vm.getAllCostBillSort()
            .collectAsStateWithLifecycle(initialValue = listOf())

        //收入分类集合
        val payoffBillSortList by vm.getAllPayoffBillSort()
            .collectAsStateWithLifecycle(initialValue = listOf())

        //头部和账单分类列表局部
        Column {
            //头部布局
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 20.dp)
            ) {
                AddBillHeaderView(
                    vm.isCost,
                    { vm.isCost = true },
                    { vm.isCost = false }
                )
                Text(text = "取消",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.clickable {
                        vm.exit()
                    }
                )
            }

            //账单列表
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.05f))
                    .padding(horizontal = 20.dp)
            ) {
                items((if (vm.isCost) costBillSortList else payoffBillSortList),
                    key = { it.id }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(15.dp)
                    ) {
                        BillSortItemView(it, it == vm.chosenSort) {
                            vm.chosenSort = it
                        }
                    }
                }
            }
        }

        if (vm.chosenSort != null) {
            //输入布局
            InputBillView(
                preDate = vm.date,
                { vm.date = it },
                { count, remark, time ->
                    vm.saveBill(count, remark, time)
                    //onBackClick()
                }
            )
        }
    }
}


/**
 * Add界面的标题
 */
@Composable
fun AddBillHeaderView(isCost: Boolean,
                      onCostClick: () -> Unit,
                      onPayOffClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable {
                    onCostClick.invoke()
                }
        ) {
            Text(
                text = "支出",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Box(
                modifier = Modifier
                    .size(width = 20.dp, height = 1.dp)
                    .background(color = if (isCost) Color.Black else Color.Transparent)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable {
                    onPayOffClick.invoke()
                }
        ) {
            Text(
                text = "收入",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Box(
                modifier = Modifier
                    .size(width = 20.dp, height = 1.dp)
                    .background(color = if (!isCost) Color.Black else Color.Transparent)
            )
        }
    }
}

/**
 * 账单分类的item
 */
@Composable
fun BillSortItemView(
    billSort: BillSort,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = CircleShape,
        border = if (isSelected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            null,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick?.invoke()
                }
        ) {
            Image(
                painter = painterResource(id = billSort.icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Text(text = billSort.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


/**
 * 输入账单布局
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBillView(
    preDate: String,
    onDateCallBack: (String) -> Unit,
    onFinish: (count: Double, remark: String, time: Long) -> Unit
) {
    val context = LocalContext.current

    var countStr by remember{mutableStateOf("0")}
    var remark by remember{mutableStateOf("")}

    var date by remember { mutableStateOf(preDate) }
    //日历选择控件
    val mCalendar = Calendar.getInstance().also { it.time = Date() }
    val mDatePickerDialog = DatePickerDialog(
        context,
        android.app.AlertDialog.THEME_HOLO_LIGHT,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            date = "$year-${month+1}-$day"
            onDateCallBack(date)
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    )


    //添加数字
    val countAppend: (Int) -> Unit = {
        countStr += it.toString()

        if (countStr.length > 1
            && countStr.startsWith("0")
            && !countStr.startsWith("0.")
        ) {
            countStr = countStr.substring(
                startIndex = 1
            )
        }
    }
    //+1
    val countAddOne: () -> Unit = {
        if (countStr.isNotEmpty()) {
            countStr.dropLastWhile { "." == it.toString() }
        }
        val count = if (countStr.isNotEmpty()) countStr.toDouble() else 0.0

        countStr = (count + 1).toString()
    }
    //-1
    val countlessOne: () -> Unit = {
        if (countStr.isNotEmpty()) {
            countStr.dropLastWhile { "." == it.toString() }
        }
        val count = if (countStr.isNotEmpty()) countStr.toDouble() else 0.0

        countStr = (count - 1).toString()
    }

    //添加小数点
    val countAddPoint = {
        if (countStr.isNotEmpty() && "." !in countStr) {
            countStr += "."
        }
    }
    //移除数字
    val countRemove: () -> Unit = {
        if(countStr.isNotEmpty()) {
            countStr = countStr.substring(
                startIndex = 0,
                endIndex = countStr.length - 1
            )
        }
    }

    //点击日期
    val onDateClick = {
        mDatePickerDialog.show()
    }
    //点击完成
    val onFinishClick = {
        //step1 check
        if (countStr.isNotEmpty()) {
            countStr.dropLastWhile { "." == it.toString() }
            //shep2 build
            onFinish(
                countStr.toDouble(), remark.trim(),
                if ("今天" == date) {
                    System.currentTimeMillis().getDayTime()
                } else {
                    date.getDayTime() ?: System.currentTimeMillis()
                }
            )
        }
    }

    //键盘网格
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            //金额
            Text(
                text = countStr,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 15.dp)
            )

            //输入框-备注
            OutlinedTextField(
                value = remark,
                onValueChange = { remark = it },
                leadingIcon  = {
                    Text("备注: ", style = MaterialTheme.typography.bodyMedium)
                },
                trailingIcon = {
                    IconButton(onClick = {

                    }){
                        Icon(Icons.Outlined.AddCircle, null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(Color.Gray.copy(alpha = 0.1f))
            )

            //键盘网格
            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(3) {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAppend(it + 7)
                            }
                    ) {
                        Text(
                            text = "${it + 7}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                onDateClick()
                            }
                    ) {
                        Row {
                            if ("今天" == date || DateUtils.getTodayDate() == date) {
                                Icon(Icons.Default.DateRange, null)
                            }
                            Text(text = if(DateUtils.getTodayDate() == date) "今天" else date)
                        }
                    }
                }

                items(3) {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAppend(it + 4)
                            }
                    ) {
                        Text("${it + 4}")
                    }
                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAddOne()
                            }
                    ) {
                        Text(text = "+",
                            style = MaterialTheme.typography.bodyLarge)
                    }
                }

                items(3) {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAppend(it + 1)
                            }
                    ) {
                        Text("${it + 1}")
                    }
                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countlessOne()
                            }
                    ) {
                        Text(text = "-",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAddPoint()
                            }
                    ) {
                        Text(text = ".",
                            style = MaterialTheme.typography.bodyLarge
                            )
                    }
                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                countAppend(0)
                            }
                    ) {
                        Text(text = "0")
                    }
                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                    ) {
                        IconButton(onClick = {
                            countRemove()
                        }) {
                            Icon(Icons.Filled.Clear, null)
                        }

                    }
                }
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .clickable {
                                onFinishClick()
                            }
                    ) {
                        Text(text = "完成")
                    }
                }
            }
        }
    }
}
