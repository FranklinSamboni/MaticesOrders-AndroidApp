package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.models.IdentificationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DocumentRepository {

    fun getDocumentTypes(completion: (list: List<IdentificationType>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = APIClient().getService().getDocumentTypes()
            val bodyReponse = call.body()
            if (call.isSuccessful) {
                val orders = bodyReponse?.data ?: emptyList()
                completion(orders)
            } else {
                Log.e("API_Error", "getDocumentTypes Error")
                completion(emptyList())
            }
        }
    }

}