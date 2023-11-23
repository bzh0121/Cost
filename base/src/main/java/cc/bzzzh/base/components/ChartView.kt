package cc.bzzzh.base.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.bzzzh.base.config.constant.TAG


@Composable
fun ChartView(modifier: Modifier = Modifier, points: List<Float>) {
    //每一行的高度
    val heightForRow = 24

    //总行数
    val countForRow = 5

    //小圆圈半径
    val circleRadius = 2.5

    //画布宽度
    val canvasWidth = LocalConfiguration.current.screenWidthDp - 8 * 2
    //画布高度
    val canvasHeight = heightForRow * countForRow + circleRadius * 2

    //没8 dp 代表1积分 每一行 3积分
    val perY = 8 //24 * 5 /15

    //七平分宽度
    val averageOfWidth = canvasWidth / 7


    Canvas(modifier = modifier.size(width = canvasWidth.dp, canvasHeight.dp), onDraw = {
        //背景横线
        for (index in 0..countForRow) {
            val startY = (index * heightForRow.toFloat() + circleRadius).dp.toPx()
            val endX = size.width
            val endY = startY
            drawLine(
                Color(0xFFEEEEEE),
                start = Offset(x = 0f, y = startY),
                end = Offset(x = endX, y = endY),
                strokeWidth = 2.5f)
        }

        //画小圆圈、折线
        for (index in 0 until points.count()) {
            Log.e(TAG, "index:${index} ");
            val centerX = (averageOfWidth * index + averageOfWidth / 2).dp.toPx()
            val centerY =
                (heightForRow * countForRow - points[index] * perY + circleRadius).dp.toPx()
            val circleCenter = Offset(centerX, centerY)
            //点
            drawCircle(Color(0xff149ee7), radius = circleRadius.dp.toPx(),
                center = circleCenter,
                style = Stroke(width = 5f)
            )
            //线
            if (index < points.count() - 1) {
                val nextPointOffsetX = (averageOfWidth * (index + 1) + averageOfWidth / 2).dp.toPx()
                val nextPointOffsetY =
                    (heightForRow * countForRow - points[(index + 1)] * perY + circleRadius).dp.toPx()
                val nextPoint = Offset(nextPointOffsetX, nextPointOffsetY)
                drawLine(Color(0xFF149EE7), start = circleCenter, end = nextPoint, strokeWidth = 5f)
            }

        }
    })
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun ChartView_Preview() {
    val weeks = listOf("02.05", "02.06", "02.07", "02.08", "02.09", "02.10", "今日")


    val pointsOfWeek = mutableStateListOf(3f, 2f, 6f, 9.3f, 10f, 15f, 8f)
    Column {
        ChartView(points = pointsOfWeek)
        Row() {
            weeks.forEach {
                Text(
                    text = it,
                    color = Color(0xff999999),
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}