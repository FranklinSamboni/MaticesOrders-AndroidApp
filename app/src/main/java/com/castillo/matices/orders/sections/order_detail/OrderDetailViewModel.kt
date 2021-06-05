package com.castillo.matices.orders.sections.order_detail

import com.castillo.matices.orders.data.AddProductToOrderRequest
import com.castillo.matices.orders.data.OrderRequest
import com.castillo.matices.orders.data.ProductRequest
import com.castillo.matices.orders.data.repositories.OrderRepository
import com.castillo.matices.orders.data.repositories.ProductRepository
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.Product

class OrderDetailViewModel {

    private val orderRepository = OrderRepository()
    private val productRepository = ProductRepository()

    fun getOrderUpdated(order: Order, completion: (order: Order?)-> Unit) {
        orderRepository.getOrderById(order.id, completion)
    }

    fun updateProduct(product: Product, completion: (success: Boolean) -> Unit) {
        val productRequest = ProductRequest(product)
        productRepository.updateProduct(productRequest) { productUpdated ->
            if (productUpdated != null) {
                completion(true)
            } else {
                completion(false)
            }
        }
    }

    fun deleteOrder(order: Order, completion: (success: Boolean) -> Unit) {
        var orderRequest = OrderRequest(order)
        orderRepository.deleteOrder(orderRequest, completion)
    }

}