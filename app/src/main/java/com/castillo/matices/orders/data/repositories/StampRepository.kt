package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.data.StampRequest
import com.castillo.matices.orders.models.Stamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StampRepository {

    val service = APIClient().getService()

    fun getStamps(completion: (list: List<Stamp>, error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.getStamps()
                val bodyReponse = call.body()
                if (call.isSuccessful) {
                    val sizes = bodyReponse?.data ?: emptyList()
                    completion(sizes, "")
                } else {
                    Log.e("API_Error", "getStamps Error " + call.errorBody())
                    completion(emptyList(), call.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("API_Error", "getStamps Error" + e.localizedMessage)
                completion(emptyList(), e.localizedMessage)
            }
        }
    }

    fun createStamp(request: StampRequest, completion: (success: Boolean, error: String)-> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.createStamp(request)
                val bodyReponse = call.body()
                if (call.isSuccessful) {
                    val response = bodyReponse?.data
                    completion(true, "")
                } else {
                    Log.e("API_Error", "createStamp Error " + call.errorBody())
                    completion(false, call.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("API_Error", "createStamp Error" + e.localizedMessage)
                completion(false, e.localizedMessage)
            }
        }
    }

    fun updateStamp(request: StampRequest, completion: (success: Boolean, error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.updateStamp(request)
                val bodyReponse = call.body()
                if (call.isSuccessful) {
                    val response = bodyReponse?.data
                    completion(true,"")
                } else {
                    Log.e("API_Error", "updateStamp Error " + call.errorBody())
                    var error = call.errorBody()?.string() ?: ""
                    if (error.contains("E11000")) {
                        error = "Ya existe un estampado con ese nombre, por favor ingresa otro nombre."
                    }
                    completion(false, error)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "updateStamp Error" + e.localizedMessage)
                completion(false, e.localizedMessage)
            }
        }
    }

    fun deleteStamp(request: StampRequest, completion: (success: Boolean, error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.deleteStamp(request)
                val bodyReponse = call.body()
                if (call.isSuccessful) {
                    val response = bodyReponse?.data
                    completion(true, "")
                } else {
                    Log.e("API_Error", "deleteStamp Error " + call.errorBody())
                    completion(false, call.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("API_Error", "deleteStamp Error" + e.localizedMessage)
                completion(false, e.localizedMessage)
            }
        }
    }

}