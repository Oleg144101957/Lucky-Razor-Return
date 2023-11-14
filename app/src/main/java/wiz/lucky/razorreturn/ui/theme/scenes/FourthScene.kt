package wiz.lucky.razorreturn.ui.theme.scenes

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FourthScene(navigation: NavHostController){

    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val text = remember {
        mutableStateOf("")
    }

    val font = FontFamily(Font(R.font.plump))

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.bg22),
            contentDescription = "bg",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    navigation.navigate(Targets.Second.route)
                }
        )

        Text(
            text = "Enter your name",
            color = Color.White,
            fontFamily = font,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-64).dp)
        )

        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

    }


}