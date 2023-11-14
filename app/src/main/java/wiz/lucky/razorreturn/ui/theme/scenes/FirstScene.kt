package wiz.lucky.razorreturn.ui.theme.scenes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wiz.lucky.razorreturn.Const
import wiz.lucky.razorreturn.R
import wiz.lucky.razorreturn.RazorActivity
import wiz.lucky.razorreturn.RazorViewModel
import wiz.lucky.razorreturn.Targets
import wiz.lucky.razorreturn.ui.theme.data.TrackManager


@Composable
fun FirstScene(navigation: NavHostController, vm: RazorViewModel){

    val a = LocalContext.current as Activity
    a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val context = LocalContext.current
    val trackManager = TrackManager(context, vm)
    val sp = context.getSharedPreferences(Const.SP, Context.MODE_PRIVATE)
    val currentData = sp.getString(Const.DESTINATION, Const.CLEAR) ?: Const.CLEAR

    val customFont = FontFamily(Font(R.font.plump))

    val rotationAnimation = remember {
        Animatable(0f)
    }


    LaunchedEffect(Unit){
        vm.liveLinkFlow.collect{
            Log.d("123123", "Launched effect 1st screen the data in vm is $it")
            if (it == Const.TOXIC){
                navigation.navigate(Targets.Second.route)
            }
        }
    }

    LaunchedEffect(Unit){
        //check remote config

        val status = Settings.Global.getString(a.contentResolver, Settings.Global.ADB_ENABLED)
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val isRazor = remoteConfig.getBoolean("isRazor")

                    Log.d("123123", "remoteconfig block isRazor $isRazor currentData = $currentData")

                    if (isRazor && status == "1"){
                        Log.d("123123", "first time remoteconfig Ok, try to start init manager")
                        val scope = MainScope()
                        scope.launch {
                            Log.d("123123", "starting scope  to buid link")
                            trackManager.initManager()
                        }

                    } else {
                        val scope = MainScope()
                        scope.launch {
                            vm.addDataToFlow(Const.TOXIC)
                        }
                    }

                }
            }
    }

    LaunchedEffect(Unit){
        rotationAnimation.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.bg11),
            contentDescription ="back",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(id = R.drawable.e7),
            contentDescription = "load",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-16).dp)
                .rotate(rotationAnimation.value)
        )

        Image(
            painter = painterResource(id = R.drawable.loadingelem),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 32.dp)
        )

        Text(
            text = "Loading...",
            fontSize = 24.sp,
            fontFamily = customFont,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}