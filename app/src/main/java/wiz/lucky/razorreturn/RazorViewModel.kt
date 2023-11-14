package wiz.lucky.razorreturn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class RazorViewModel : ViewModel() {

    val liveLinkFlow = MutableSharedFlow<String>()

    suspend fun addDataToFlow(data: String){
        liveLinkFlow.emit(data)
    }
}