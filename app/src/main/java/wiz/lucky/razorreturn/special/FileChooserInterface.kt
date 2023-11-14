package wiz.lucky.razorreturn.special


import android.net.Uri
import android.webkit.ValueCallback

interface FileChooserInterface {

    fun onFileCallback(parameters: ValueCallback<Array<Uri?>>)

}