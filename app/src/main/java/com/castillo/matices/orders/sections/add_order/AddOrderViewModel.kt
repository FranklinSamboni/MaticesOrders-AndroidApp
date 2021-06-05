package com.castillo.matices.orders.sections.add_order

import android.util.Log
import com.castillo.matices.orders.data.ClientRequest
import com.castillo.matices.orders.data.repositories.DocumentRepository
import com.castillo.matices.orders.data.repositories.OrderRepository
import com.castillo.matices.orders.data.OrderRequest
import com.castillo.matices.orders.data.repositories.ClientRepository
import com.castillo.matices.orders.models.*

class AddOrderViewModel {

    private val orderRepository = OrderRepository()
    private val documentRepository = DocumentRepository()
    private val clientRepository = ClientRepository()

    fun getDocumentTypes(completion: (List<IdentificationType>) -> Unit) {
        documentRepository.getDocumentTypes(completion);
    }

    fun saveOrder(order: Order, client: Client, completion: (success: Boolean) -> Unit) {

        client.phoneCode = PhoneCode("+57")

        order.statusEnumEnum = OrderStatusEnum.RECEIVED
        order.client = client

        val orderRequest = OrderRequest(order)
        var clientRequest = ClientRequest(client)
        if (order.id.isEmpty() && client.id.isEmpty()) {
            addOrder(orderRequest, clientRequest, completion)
        } else if (!order.id.isEmpty() && !client.id.isEmpty())  {
            updateOrder(orderRequest, clientRequest, completion)
        } else {
            Log.e("Error Save Order", "Los datos de la orden y el cliente no estan sincronizados")
            completion(false)
        }
    }

    private fun addOrder(orderRequest: OrderRequest, clientRequest: ClientRequest, completion: (success: Boolean) -> Unit) {
        clientRepository.createClient(clientRequest) { client ->
            if (client != null) {
                orderRequest.clientId = client.id
                orderRepository.addOrder(orderRequest, completion)
            } else {
                Log.e("Error", "No fue posible crear el cliente")
                completion(false)
            }
        }
    }

    private fun updateOrder(orderRequest: OrderRequest, clientRequest: ClientRequest, completion: (success: Boolean) -> Unit) {
        clientRepository.updateClient(clientRequest) { client ->
            if (client != null) {
                orderRequest.clientId = client.id
                orderRepository.updateOrder(orderRequest, completion)
            } else {
                Log.e("Error", "No fue posible actualizar el cliente")
                completion(false)
            }
        }
    }
}