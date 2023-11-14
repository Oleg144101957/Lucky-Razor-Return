package wiz.lucky.razorreturn.ui.theme.data

import android.content.Context
import android.os.Build
import android.provider.Settings

class StatisticProvider(context: Context) {

    val ADB_ENABLED = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
    val BOOT_COUNT = Settings.Global.getString(context.contentResolver, Settings.Global.BOOT_COUNT)

    fun makeTukTuk(): String {
        val fingerprint = Build.FINGERPRINT
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return "Model: $model FINGERPRINT $fingerprint ADB: $ADB_ENABLED Boots: $BOOT_COUNT MANUFACTURER: $manufacturer"
    }
}