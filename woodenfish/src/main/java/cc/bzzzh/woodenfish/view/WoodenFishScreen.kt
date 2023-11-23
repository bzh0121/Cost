package cc.bzzzh.woodenfish.view

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cc.bzzzh.base.config.constant.TAG
import cc.bzzzh.base.util.SpUtils
import cc.bzzzh.base.util.VibratorUtil
import cc.bzzzh.woodenfish.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * 木鱼界面
 */
@Composable
fun WoodenFishScreen(
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    //rememberUpdatedState可以在不中断副作用的前提下感知外界变化。
    //即：可以感知到MaterialTheme.colorScheme.primary的值变化，但也不会影响重组。
    val primary by rememberUpdatedState(MaterialTheme.colorScheme.primary)
    val systemUiController = rememberSystemUiController()
    DisposableEffect(lifecycleOwner) {
        // compose的生命周期监听器，来改变状态栏颜色
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                systemUiController.setStatusBarColor(Color.Black)
                Log.d(TAG, "WoodenFishScreen: ON_START")
            } else if (event == Lifecycle.Event.ON_STOP) {
                systemUiController.setStatusBarColor(primary)
                Log.d(TAG, "WoodenFishScreen: ON_STOP")
            }
        }
        //添加生命周期监听
        lifecycleOwner.lifecycle.addObserver(observer)
        //清理
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    //总数
    var allNumber by remember {
        mutableStateOf(SpUtils.getInt("gd_count", 0))
    }

    //当前数目
    var dailyNumber by remember {
        mutableStateOf(0)
    }
    //木鱼图片尺寸
    val fishSizeAnim = remember {
        Animatable(250.dp, Dp.VectorConverter)
    }
    //+1文字底部填充尺寸
    val plusOneBottom = remember(dailyNumber) {
        Animatable(150.dp, Dp.VectorConverter)
    }
    //+1文字透明度
    val plusOneAlpha = remember {
        Animatable(0f)
    }
    //+1文字尺寸
    val plusOneFontSize = remember(dailyNumber) {
        Animatable(35f)
    }

    //动画的改变需要在协程中使用
    //木鱼图片尺寸改变动画
    LaunchedEffect(dailyNumber) {
        fishSizeAnim.animateTo(280.dp, initialVelocity = 50.dp)
        fishSizeAnim.snapTo(250.dp)
    }
    //+1底部填充改变动画
    LaunchedEffect(dailyNumber) {
        plusOneBottom.animateDecay(850.dp, exponentialDecay())
    }
    //+1文字透明度改变动画
    LaunchedEffect(dailyNumber) {
        plusOneAlpha.snapTo(10f)
        plusOneAlpha.animateTo(0f)
    }
    //+1文字大小改变动画
    LaunchedEffect(dailyNumber) {
        plusOneFontSize.animateTo(25f)
    }

    //view
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            NumberTitleView(allNumber, dailyNumber)
            WoodenFishView(
                fishSizeAnim,
                plusOneBottom,
                plusOneAlpha,
                plusOneFontSize
            ) {
                VibratorUtil.startVibrator(context)
                dailyNumber++
                allNumber++
                SpUtils.put("gd_count", allNumber)
            }
        }
    }
}


/**
 * 数目标题
 */
@Composable
private fun NumberTitleView(allNumber: Int, dailyNumber: Int) {
    Text(
        text = "累计功德 $allNumber 次",
        color = Color.White,
        fontSize = 15.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(top = 40.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = "本次功德 $dailyNumber 次",
        color = Color.White,
        fontSize = 25.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(top = 5.dp),
        textAlign = TextAlign.Center
    )
}

/**
 * 木鱼view
 */
@Composable
private fun WoodenFishView(
    fishSizeAnim: Animatable<Dp, AnimationVector1D>,
    plusOneBottom: Animatable<Dp, AnimationVector1D>,
    plusOneAlpha: Animatable<Float, AnimationVector1D>,
    plusOneFontSize: Animatable<Float, AnimationVector1D>,
    clickable: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_fish),
            contentDescription = "木鱼",
            modifier = Modifier
                .size(fishSizeAnim.value)
                .clickable {
                    clickable()
                })
        Text(
            text = "功德 +1",
            Modifier
                .padding(bottom = plusOneBottom.value)
                .alpha(plusOneAlpha.value),
            color = Color.White,
            fontSize = plusOneFontSize.value.sp,
        )
    }
}

@Preview
@Composable
private fun WoodenFish_Preview() {
    Box(modifier =  Modifier.background(Color.Gray.copy(alpha = 0.5f))) {
        NumberTitleView(9, 919)
    }
}