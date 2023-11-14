package wiz.lucky.razorreturn.ui.theme.scenes

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import wiz.lucky.razorreturn.Targets


@Composable

fun SixthScene(navigation: NavHostController){

    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    AndroidView(factory = {context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                }
            }

            webChromeClient = object : WebChromeClient(){

            }

            loadUrl("file:///android_asset/score.html")

        }
    })

    Box(modifier = Modifier.fillMaxSize()){
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.Close),
            contentDescription = "btn close",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(48.dp)
                .clickable {
                    navigation.navigate(Targets.Second.route)
                }
        )
    }
}