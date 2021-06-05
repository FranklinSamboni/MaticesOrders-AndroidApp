package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.models.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SizeRepository {

    fun getSizes(completion: (list: List<Size>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = APIClient().getService().getSizes()
            val bodyReponse = call.body()
            if (call.isSuccessful) {
                val sizes = bodyReponse?.data ?: emptyList()
                completion(sizes)
            } else {
                Log.e("API_Error", "getSizes Error")
                completion(emptyList())
            }
        }
    }

}