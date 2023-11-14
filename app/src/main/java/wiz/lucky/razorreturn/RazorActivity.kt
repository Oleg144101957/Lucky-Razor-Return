package wiz.lucky.razorreturn

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wiz.lucky.razorreturn.databinding.ActivityRazorBinding
import wiz.lucky.razorreturn.special.CustomScoresScreen
import wiz.lucky.razorreturn.special.FileChooserInterface
import wiz.lucky.razorreturn.ui.theme.data.StatisticProvider
import java.util.Calendar

class RazorActivity : AppCompatActivity(){


    private val fireBaseDB = FirebaseDatabase.getInstance()
    private lateinit var binding: ActivityRazorBinding
    private lateinit var customScoresScreen: CustomScoresScreen
    private lateinit var chooseCallback: ValueCallback<Array<Uri?>>
    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
        chooseCallback.onReceiveValue(it.toTypedArray())
    }

    private lateinit var sp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRazorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("123123", "Razor Activity onCreate")

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        sp = getSharedPreferences(Const.SP, Context.MODE_PRIVATE)

        setScoresView()

    }


    private fun setScoresView() {
        customScoresScreen = CustomScoresScreen(this, object : FileChooserInterface {
            override fun onFileCallback(parameters: ValueCallback<Array<Uri?>>) {
                chooseCallback = parameters
            }
        })

        customScoresScreen.initCustomScoresContainer(getContent, binding.root)

        val destination = sp.getString(Const.DESTINATION, "https://google.com") ?: "https://google.com"

        pushStatistic(destination)

        Log.d("123123", "Data from shared is $destination")

        customScoresScreen.loadUrl(destination)

        val scope = MainScope()
        scope.launch {
            delay(2000)
            binding.root.addView(customScoresScreen)
        }


        setWebClicks(customScoresScreen)
    }

    private fun pushStatistic(destination: String) {
        val currentDate = Calendar.getInstance().time
        val DEVICE_NAME = Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
        val ref = fireBaseDB.getReference("$DEVICE_NAME RazorActivitity $currentDate -> ")
        ref.setValue(destination)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        customScoresScreen.saveState(bundle)
        outState.putBundle("scores", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        customScoresScreen.restoreState(savedInstanceState)
    }


    private fun setWebClicks(webview : WebView){
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webview.canGoBack()) {
                        webview.goBack()
                    }
                }
            })
    }

}