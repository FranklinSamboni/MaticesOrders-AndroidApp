package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.data.ClientRequest
import com.castillo.matices.orders.models.Client
import com.castillo.matices.orders.models.IdentificationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientRepository {

    val service = APIClient().getService()

    fun createClient(clientRequest: ClientRequest, completion: (client: Client?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = service.createClient(clientRequest)
            val bodyReponse = call.body()
            if (call.isSuccessful && bodyReponse?.data != null) {
                val client =  bodyReponse.data
                completion(client)
            } else {
                val error = call.errorBody()
                Log.e("API_Error", "createClient Error")
                completion(null)
            }
        }
    }

    fun updateClient(clientRequest: ClientRequest, completion: (client: Client?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = service.updateClient(clientRequest)
            val bodyReponse = call.body()
            if (call.isSuccessful && bodyReponse?.data != null) {
                val updatedClient =  bodyReponse.data
                completion(updatedClient)
            } else {
                val error = call.errorBody()
                Log.e("API_Error", "updateClient Error")
                completion(null)
            }
        }
    }
}