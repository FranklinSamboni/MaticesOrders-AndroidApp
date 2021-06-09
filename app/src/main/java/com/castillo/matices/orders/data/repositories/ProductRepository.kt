package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.data.OrderRequest
import com.castillo.matices.orders.data.ProductRequest
import com.castillo.matices.orders.models.Product
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.delete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class ProductRepository {

    val service = APIClient().getService()

    fun createProduct(productRequest: ProductRequest, completion: (product: Product?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.createProduct(productRequest)
                val bodyReponse = call.body()
                if (call.isSuccessful && bodyReponse?.data != null) {
                    val product = bodyReponse.data
                    completion(product)
                } else {
                    Log.e("API_Error", "createProduct Error " + call.errorBody()?.string())
                    completion(null)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "createProduct Error " + e.localizedMessage)
                completion(null)
            }
        }
    }

    fun updateProduct(productRequest: ProductRequest, completion: (product: Product?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.updateProduct(productRequest)
                val bodyReponse = call.body()
                if (call.isSuccessful && bodyReponse?.data != null) {
                    val product = bodyReponse.data
                    saveProductLocally(product) {
                        completion(product)
                    }

                } else {
                    Log.e("API_Error", "updateProduct Error " + call.errorBody()?.string())
                    completion(null)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "updateProduct Error " + e.localizedMessage)
                completion(null)
            }
        }
    }

    fun deleteProduct(productRequest: ProductRequest, completion: (success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.deleteProduct(productRequest)
                val bodyReponse = call.body()
                if (call.isSuccessful && bodyReponse?.data != null) {
                    deleteProductLocally(productRequest.product) {
                        completion(true)
                    }

                } else {
                    Log.e("API_Error", "deleteProduct Error " + call.errorBody()?.string())
                    completion(false)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "deleteProduct Error " + e.localizedMessage)
                completion(false)
            }
        }
    }

    private fun saveProductLocally(product: Product, completion: () -> Unit) {
        val runnable = Runnable {
            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                backgroundThreadRealm.executeTransaction { transactionRealm ->
                    transactionRealm.insertOrUpdate(product)
                }
                backgroundThreadRealm.close()
            }

            completion()
        }

        val task : FutureTask<String> = FutureTask(runnable, "saveProduct")
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        executorService.execute(task)
    }

    private fun deleteProductLocally(product: Product, completion: () -> Unit) {
        val runnable = Runnable {
            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                backgroundThreadRealm.executeTransaction { transactionRealm ->
                    var productQ = transactionRealm.where(Product::class.java)
                            .equalTo("id", product.id)
                            .findFirst()
                    productQ?.deleteFromRealm()
                    productQ = null
                }
                backgroundThreadRealm.close()
            }

            completion()
        }

        val task : FutureTask<String> = FutureTask(runnable, "saveProduct")
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        executorService.execute(task)
    }

    private fun getRealmInstance(): Realm? {
        try {
            val config = RealmConfiguration.Builder().build()
            val backgroundThreadRealm : Realm = Realm.getInstance(config)
            return backgroundThreadRealm
        } catch (e: Exception) {
            Log.e("Error Realm",e.localizedMessage)
            return null
        }
    }


}