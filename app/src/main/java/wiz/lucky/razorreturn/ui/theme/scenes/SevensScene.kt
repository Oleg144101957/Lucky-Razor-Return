package wiz.lucky.razorreturn.ui.theme.scenes

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import wiz.lucky.razorreturn.R
import wiz.lucky.razorreturn.Targets


@Composable
fun SevensScene(navigation: NavHostController) {

    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painterResource(id = R.drawable.bg22),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )


        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
                .size(64.dp)
                .clickable {
                    navigation.navigate(Targets.Second.route)
                }
        )

        val font = FontFamily(Font(R.font.plump))

        Text(
            text = "Lucky Razor Return is an exciting Android game that challenges players with a unique puzzle-solving experience. Set in a vibrant and magical world, the game's main goal is to strategically match and combine various elements to unleash their hidden powers. Navigate through enchanting levels as you discover new combinations, harnessing the power of elements to overcome obstacles and achieve victory.",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = font,
            modifier = Modifier
                .align(Alignment.Center)
        )


    }






}