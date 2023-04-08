package cc.bzzzh.daily.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import cc.bzzzh.base.config.constant.FormatConstant
import cc.bzzzh.base.data.model.Bill
import cc.bzzzh.base.data.model.BillWithSort
import cc.bzzzh.base.data.model.DailyCountTuple
import cc.bzzzh.base.util.DateUtils
import cc.bzzzh.base.util.ktx.getDayStr
import cc.bzzzh.base.util.ktx.getWeek
import cc.bzzzh.daily.R
import cc.bzzzh.daily.intent.DailyAction
import cc.bzzzh.daily.viewmodel.DailyBillVM


/**
 * 明细界面
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyBillScreen(
    vm: DailyBillVM,
    action: DailyAction,
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

    //账单集合
    val billList = vm.getBillList().collectAsLazyPagingItems()
    //每日总数
    val dailyCountList by vm.getDailySumCount().collectAsStateWithLifecycle(initialValue = listOf())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            //标题栏
            DailyBillHeaderView(
                action.openSearchScreen,
                action.openDateScreen
            )
            //汇总统计
            DailyBillSumUpView(vm)
            //快捷链接
            DailyBillLinkView()

            //账单列表
            DailyListView(billList, dailyCountList) {
                vm.delBill(it)
            }
        }
    }
}



/**
 * 明细界面标题栏
 */
@Composable
fun DailyBillHeaderView(
    onSearchClick: () -> Unit,
    onDateClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge)
        Row(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(30.dp))
            Icon(imageVector = Icons.Rounded.DateRange, contentDescription = null,
                modifier = Modifier.clickable {
                    onDateClick.invoke()
                }
            )
        }
    }
}

/**
 * 汇总数据
 */
@Composable
fun DailyBillSumUpView(vm: DailyBillVM) {
    val context = LocalContext.current

    //var openDialog by remember{ mutableStateOf(false) }

    val monthCostCount by vm.getMonthCostCount().collectAsStateWithLifecycle(initialValue = 0.00)

    //用stateflow时 显示数据会闪烁
    /*val lifecycleOwner = LocalLifecycleOwner.current
    val monthCostCount by remember(vm.getMonthCostCount(), lifecycleOwner) {
        vm.getMonthCostCount()
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .stateIn(
                scope = vm.viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.00
            )
    }.collectAsState()*/

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.clickable {
                val datePicker = DatePickerDialog(
                    context,
                    android.app.AlertDialog.THEME_HOLO_LIGHT,
                    { _, year, month, _ ->
                        vm.year = year
                        vm.month = month + 1
                    },
                    vm.year,
                    vm.month -1,
                    1
                    )
                datePicker.show()
                ((datePicker.datePicker.getChildAt(0) as? ViewGroup)?.
                getChildAt(0) as? ViewGroup)?.
                getChildAt(2)?.visibility = View.GONE
            }
        ) {
            Text(text = "${vm.year}年",
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.secondary
            )
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = DateUtils.getMonthValue(vm.month),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "月",
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )

                Box(modifier = Modifier
                    .size(width = 21.dp, height = 20.dp)
                    .padding(horizontal = 10.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "收入",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "0",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = ".00",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "支出",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(verticalAlignment = Alignment.Bottom) {
                val count = FormatConstant.df2_00.format(monthCostCount ?: 0.00)
                    .split(".")
                Text(
                    text = count[0],
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (count.size > 1) {
                    Text(
                        text = ".${count[1]}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }

    /*if(openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false }
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(factory = {context ->
                    //todo？怎么给这个设置成 android:datePickerMode="spinner"
                    //看下view构造流程，构建个attrs参数传进去
                    DatePicker(context).apply {

                    }
                })
            }
        }
    }*/
}

/**
 * 账单等链接
 */
@Composable
fun DailyBillLinkView() {
    Box(modifier = Modifier
        .background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.background
                ),
                startY = 0.6f
            )
        )
        .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Card(modifier = Modifier
            .fillMaxWidth(),
            colors =  CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(5.dp),
            elevation =  CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (index in 0..4) {
                    LinkItemView()
                }
            }
        }
    }
}

/**
 * 账单列表
 */
@Composable
fun DailyListView(
    billList: LazyPagingItems<BillWithSort>,
    dailyCountList: List<DailyCountTuple>,
    onDel: (Bill) -> Unit
) {

    LazyColumn {
        itemsIndexed(billList, key = { _, data -> data.bill.id }) {index, data->
            data?.let {
                val showGap = if (index == 0) {
                    true
                } else {
                    val lastBill =  billList[index - 1]
                    (lastBill?.bill?.time ?: 0) - data.bill.time >= 24*60*60*1000
                }
                Column {
                    if (showGap) {
                        dailyCountList.find {
                            data.bill .time == it.time
                        }?.let {
                            BillItemDayTitleView(it)
                        }
                    }
                    BillItemView(data) {
                        onDel.invoke(it)
                    }
                }
            }
        }
    }
}

/**
 * 账单列表
 */
@Composable
fun DailyListView1(vm: DailyBillVM) {
    //账单集合
    val billList = vm.getBillList().collectAsLazyPagingItems()
    //每日总数
    val dailyCountList by vm.getDailySumCount().collectAsStateWithLifecycle(initialValue = listOf())

    LazyColumn {
        itemsIndexed(billList, key = { _, data -> data.bill.id }) {index, data->
            data?.let {
                val showGap = if (index == 0) {
                    true
                } else {
                    val lastBill =  billList[index - 1]
                    (lastBill?.bill?.time ?: 0) - data.bill.time >= 24*60*60*1000
                }
                Column {
                    if (showGap) {
                        dailyCountList.find {
                            data.bill .time == it.time
                        }?.let {
                            BillItemDayTitleView(it)
                        }
                    }
                    BillItemView(data) {
                        vm.delBill(it)
                    }
                }
            }
        }
    }
}


/**
 * 快捷链接点击项
 */
@Composable
fun RowScope.LinkItemView() {
    Column(modifier = Modifier
        .padding(10.dp)
        .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.bottom_items),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Text("账单", style = MaterialTheme.typography.bodySmall)
    }
}

/**
 * 每日统计标题
 */
@Composable
fun BillItemDayTitleView(dailyCountList: DailyCountTuple){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${dailyCountList.time.getDayStr()} ${dailyCountList.time.getWeek()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "支出：${dailyCountList.sumCount}",
                color = Color.Gray.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray.copy(alpha = 0.2f)))
    }
}

/**
 * 每条账单布局
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillItemView(bean: BillWithSort, onDel: (Bill) -> Unit) {
    Row(
        modifier = Modifier
            .combinedClickable(
                enabled = true,
                onLongClick = {
                    onDel.invoke(bean.bill)
                },
                onDoubleClick = {
                },
                onClick = {
                }
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(bean.sort.icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = bean.bill.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )
        Text(text = "-${bean.bill.count}")
    }
}