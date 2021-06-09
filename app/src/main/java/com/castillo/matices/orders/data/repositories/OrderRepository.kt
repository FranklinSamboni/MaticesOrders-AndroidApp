package com.castillo.matices.orders.data.repositories

import android.util.Log
import com.castillo.matices.orders.data.APIClient
import com.castillo.matices.orders.data.AddProductToOrderRequest
import com.castillo.matices.orders.data.OrderRequest
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.Product
import io.realm.*
import io.realm.kotlin.where
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class OrderRepository {

    val service = APIClient().getService()

    fun addOrder(orderRequest: OrderRequest, completion: (success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.addOrder(orderRequest)
                val bodyResponse = call.body()
                if (call.isSuccessful && bodyResponse?.data != null) {
                    val order = bodyResponse.data
                    saveOrderLocally(order) {
                        completion(true)
                    }
                } else {
                    Log.e("API_Error", "AddOrderAPI Error " + call.errorBody()?.string())
                    completion(false)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "AddOrderAPI Error " + e.localizedMessage)
                completion(false)
            }
        }
    }

    fun updateOrder(orderRequest: OrderRequest, completion: (success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.updateOrder(orderRequest)
                val bodyResponse = call.body()
                if (call.isSuccessful && bodyResponse?.data != null) {
                    val order = bodyResponse.data
                    saveOrderLocally(order) {
                        completion(true)
                    }
                } else {
                    Log.e("API_Error", "AddOrderAPI Error " +  call.errorBody()?.string())
                    completion(false)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "AddOrderAPI Error " +  e.localizedMessage)
                completion(false)
            }
        }
    }



    fun getOrders(completion: (list: List<Order>, error: String) -> Unit) {

        // TODO: REMOVER PARA VALIDAR CON SI HAY O NO INTERNET
        if (true) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val call = service.getOrders()
                    val bodyResponse = call.body()
                    if (call.isSuccessful) {
                        val orders = bodyResponse?.data ?: emptyList()
                        saveOrdersLocally(orders) {
                            completion(orders, "")
                        }

                    } else {
                        Log.e("API_Error", "GetOrderAPI Error " + call.errorBody()?.string())
                        completion(emptyList(), call.errorBody()?.string() ?: "")
                    }
                } catch (e: Exception) {
                    Log.e("API_Error", "GetOrderAPI Error" + e.localizedMessage)
                    completion(emptyList(), e.localizedMessage)
                }
            }

        } else {
            val runnable = Runnable {
                val backgroundThreadRealm: Realm? = getRealmInstance()
                if (backgroundThreadRealm != null) {
                    val ordersRealm: RealmResults<Order> = backgroundThreadRealm.where<Order>().findAll().sort("dateCreated", Sort.DESCENDING)
                    val orders = backgroundThreadRealm.copyFromRealm(ordersRealm)
                    backgroundThreadRealm.close()
                    completion(orders, "")
                } else {
                    completion(emptyList(), "")
                }
            }

            val task : FutureTask<String> = FutureTask(runnable, "getOrders")
            val executorService: ExecutorService = Executors.newFixedThreadPool(2)
            executorService.execute(task)
        }
    }

    fun getOrderById(id: String, completion: (order: Order?) -> Unit) {
        val runnable = Runnable {

            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                val query: RealmQuery<Order> = backgroundThreadRealm.where<Order>().equalTo("id",id)
                val order = backgroundThreadRealm.copyFromRealm(query.findFirst())
                backgroundThreadRealm.close()
                completion(order)
            } else {
                completion(null)
            }
        }

        val task : FutureTask<String> = FutureTask(runnable, "getOrderById")
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        executorService.execute(task)
    }

    fun addProducToOrder(request: AddProductToOrderRequest, completion: (success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.addProductToOrder(request)
                val bodyResponse = call.body()
                if (call.isSuccessful && bodyResponse?.data != null) {
                    val order = bodyResponse.data
                    saveOrderLocally(order) {
                        completion(true)
                    }
                } else {
                    Log.e("API_Error", "addProducToOrder Error " + call.errorBody()?.string())
                    completion(false)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "addProducToOrder Error " + e.localizedMessage)
                completion(false)
            }
        }
    }

    fun deleteOrder(orderRequest: OrderRequest, completion: (success: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = service.deleteOrder(orderRequest)
                val bodyResponse = call.body()
                if (call.isSuccessful && bodyResponse?.data != null) {
                    deleteOrderLocally(orderRequest.order) {
                        completion(true)
                    }
                } else {
                    Log.e("API_Error", "deleteOrder Error " + call.errorBody()?.string())
                    completion(false)
                }
            } catch (e: Exception) {
                Log.e("API_Error", "deleteOrder Error " + e.localizedMessage)
                completion(false)
            }

        }
    }

    private fun saveOrdersLocally(orders: List<Order>, completion: () -> Unit) {
        val runnable = Runnable {

            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                backgroundThreadRealm.executeTransaction { transactionRealm ->
                    transactionRealm.insertOrUpdate(orders)
                }
                backgroundThreadRealm.close()
            }
            completion()
        }

        val task : FutureTask<String> = FutureTask(runnable, "saveOrders")
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        executorService.execute(task)
    }

    private fun saveOrderLocally(order: Order, completion: () -> Unit) {
        val runnable = Runnable {

            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                backgroundThreadRealm.executeTransaction { transactionRealm ->
                    transactionRealm.insertOrUpdate(order)
                }
                backgroundThreadRealm.close()
            }
            completion()
        }

        val task : FutureTask<String> = FutureTask(runnable, "addOrder")
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        executorService.execute(task)
    }

    private fun deleteOrderLocally(order: Order, completion: () -> Unit) {
        val runnable = Runnable {

            val backgroundThreadRealm: Realm? = getRealmInstance()
            if (backgroundThreadRealm != null) {
                backgroundThreadRealm.executeTransaction { transactionRealm ->
                    var orderQ = transactionRealm.where(Order::class.java)
                            .equalTo("id", order.id)
                            .findFirst()
                    orderQ?.deleteFromRealm()
                    orderQ = null
                }
                backgroundThreadRealm.close()
            }
            completion()
        }

        val task : FutureTask<String> = FutureTask(runnable, "addOrder")
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