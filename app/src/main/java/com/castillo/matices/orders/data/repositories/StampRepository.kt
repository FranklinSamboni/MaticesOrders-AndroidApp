package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.models.Stamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StampRepository {

    fun getStamps(completion: (list: List<Stamp>, error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = APIClient().getService().getStamps()
                val bodyReponse = call.body()
                if (call.isSuccessful) {
                    val sizes = bodyReponse?.data ?: emptyList()
                    completion(sizes, "")
                } else {
                    Log.e("API_Error", "getSizes Error " + call.errorBody())
                    completion(emptyList(), call.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e("API_Error", "getSizes Error" + e.localizedMessage)
                completion(emptyList(), e.localizedMessage)
            }
        }
    }

}