package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.models.Color
import com.castillo.matices.orders.models.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColorRepository {

    fun getColors(completion: (list: List<Color>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = APIClient().getService().getColors()
            val bodyReponse = call.body()
            if (call.isSuccessful) {
                val colors = bodyReponse?.data ?: emptyList()
                completion(colors)
            } else {
                Log.e("API_Error", "getColors Error")
                completion(emptyList())
            }
        }
    }

}