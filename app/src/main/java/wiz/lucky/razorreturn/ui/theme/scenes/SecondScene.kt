package wiz.lucky.razorreturn.ui.theme.scenes

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import wiz.lucky.razorreturn.Const
import wiz.lucky.razorreturn.R
import wiz.lucky.razorreturn.Targets
import wiz.lucky.razorreturn.ui.theme.RazorWhite


@Composable
fun SecondScene(navigation: NavHostController){

    val a = LocalContext.current as Activity
    a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.bg22),
            contentDescription = "launcher back",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )



        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 32.dp)
        ) {
            
            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.e4),
                    contentDescription = "stat",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(0.7f)
                        .clickable {
                            navigation.navigate(Targets.Sixth.route)
                        }
                )
                
            }
            
            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.basegame22),
                    contentDescription = "btn_bg",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            navigation.navigate(Targets.Third.route)
                        }
                )
                
                Text(
                    text = "Play",
                    color = RazorWhite,
                    fontFamily = Const.customFont,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.basegame22),
                    contentDescription = "btn_bg",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            navigation.navigate(Targets.Fourth.route)
                        }
                )

                Text(
                    text = "Settings",
                    color = RazorWhite,
                    fontFamily = Const.customFont,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.basegame22),
                    contentDescription = "btn_bg",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            navigation.navigate(Targets.Sevens.route)
                        }
                )

                Text(
                    text = "Help",
                    color = RazorWhite,
                    fontFamily = Const.customFont,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}