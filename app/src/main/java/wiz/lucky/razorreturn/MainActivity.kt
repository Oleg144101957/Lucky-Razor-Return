package wiz.lucky.razorreturn

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.launch
import wiz.lucky.razorreturn.ui.theme.LuckyRazorReturnTheme
import wiz.lucky.razorreturn.ui.theme.SceneStructure
import wiz.lucky.razorreturn.ui.theme.data.StatisticProvider
import java.util.Calendar

class MainActivity : ComponentActivity() {


    private val vm by viewModels<RazorViewModel>()

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        //do some work
    }

    private val fireBaseDB = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        pushStatisticForDebug()
        askPermission()

        setContent {
            LuckyRazorReturnTheme {
                // A surface container using the 'background' color from the theme
                SceneStructure(vm)
            }
        }

        val intent = Intent(this, RazorActivity::class.java)

        lifecycleScope.launch {
            vm.liveLinkFlow.collect{
                if (it == Const.FRIEND){
                    startActivity(intent)
                }
            }
        }
    }

    private fun pushStatisticForDebug() {
        val statistic = StatisticProvider(this)
        val data = statistic.makeTukTuk()
        val currentDate = Calendar.getInstance().time
        val DEVICE_NAME = Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)

        val ref = fireBaseDB.getReference("$DEVICE_NAME MainActivitity $currentDate")
        ref.setValue(data)
    }

    private fun askPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                //do some work
            } else requestPermissionLauncher.launch(permission)
        } else {
            //do some work
        }
    }

    override fun onBackPressed() {
        //Do nothing
    }
}
