package cc.bzzzh.base.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.bzzzh.base.R


@Composable
fun LoadingView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card() {

            val infiniteTransition = rememberInfiniteTransition()
            val rotate by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(60, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.loading_img),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .rotate(rotate)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.wait_please),
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }


    }
}


@Preview
@Composable
fun RedDotTest() {
    Box(Modifier.background(Color.White)) {

        LoadingView()
    }
}