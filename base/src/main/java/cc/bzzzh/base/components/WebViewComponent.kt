package cc.bzzzh.base.components

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cc.bzzzh.base.config.theme.CostTheme


/**
 * 生命周期监听器
 */
@Composable
private fun rememberWebViewLifecycleObserver(webView: WebView): LifecycleEventObserver {
    return remember(webView) {
        LifecycleEventObserver { source, event ->
            run {
                when(event) {
                    Lifecycle.Event.ON_RESUME -> webView.onResume()
                    Lifecycle.Event.ON_PAUSE -> webView.onPause()
                    Lifecycle.Event.ON_DESTROY -> webView.destroy()
                    else -> {}
                }
            }
        }
    }
}

/**
 * 监听了compose生命周期的webView
 */
@Composable
fun rememberWebViewWIthLifecycle(): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context)
    }
    val lifecycleObserver = rememberWebViewLifecycleObserver(webView = webView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return webView
}

/**
 * 通过AndroidView使用webView
 *
 * （后来发现accompanist有webVIew插件）
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(url: String) {
    val webView = rememberWebViewWIthLifecycle()
    AndroidView(factory = {
        webView
    }, modifier = Modifier
        .fillMaxSize(),
        update = {
            val webSetting = it.settings
            webSetting.javaScriptEnabled = true
            it.loadUrl(url)
            it.webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return if (view != null && request != null) {
                        view.loadUrl(request.url.toString())
                        true
                    } else {
                        super.shouldOverrideUrlLoading(view, request)
                    }
                }
            }
        }
    )
}



/**
 * 预览
 */
@Preview
@Composable
private fun WebViewPage_PreView() {
    CostTheme() {
        WebViewPage(url = "https://m.zhibo8.cc")
    }
}