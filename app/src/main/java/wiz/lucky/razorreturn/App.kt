package wiz.lucky.razorreturn

import android.app.Application
import com.onesignal.OneSignal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(SIGNAL1+SIGNAL2)
    }

    companion object{
        val SIGNAL1 = "546e8c8f-3341"
        val SIGNAL2 = "-42e6-a839-710846420184"
        val APPSDEVKEY1 = "FeUwMV47hj2"
        val APPSDEVKEY2 = "MSRGCC8YE2N"
    }
}