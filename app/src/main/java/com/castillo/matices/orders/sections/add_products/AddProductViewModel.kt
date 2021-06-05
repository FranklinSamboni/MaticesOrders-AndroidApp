package com.castillo.matices.orders.sections.add_products

import com.castillo.matices.orders.data.AddProductToOrderRequest
import com.castillo.matices.orders.data.ProductRequest
import com.castillo.matices.orders.data.repositories.ColorRepository
import com.castillo.matices.orders.data.repositories.OrderRepository
import com.castillo.matices.orders.data.repositories.ProductRepository
import com.castillo.matices.orders.data.repositories.SizeRepository
import com.castillo.matices.orders.models.*
import java.util.*

class AddProductViewModel {

    private val orderRepository = OrderRepository()
    private val productRepository = ProductRepository()
    private val colorRepository = ColorRepository()
    private val sizeRepository = SizeRepository()

    fun getSizes(completion: (sizes: List<Size>) -> Unit) {
        return sizeRepository.getSizes(completion)
    }

    fun getColors(completion: (colors: List<Color>) -> Unit) {
        return colorRepository.getColors(completion)
    }

    fun saveProduct(order: Order, product: Product, completion: (success: Boolean) -> Unit) {

        val productRequest = ProductRequest(product)
        if (product.id.isEmpty()) {
            productRepository.createProduct(productRequest) { productCreated ->
                if (productCreated != null) {
                    val addProducToOrderRequest = AddProductToOrderRequest(order, productCreated)
                    orderRepository.addProducToOrder(addProducToOrderRequest, completion)
                } else {
                    completion(false)
                }
            }
        } else {
            productRepository.updateProduct(productRequest) { productUpdated ->
                if (productUpdated != null) {
                    completion(true)
                } else {
                    completion(false)
                }
            }
        }
    }

    fun deleteProduct(product: Product, completion: (success: Boolean) -> Unit) {
        val productRequest = ProductRequest(product)
        productRepository.deleteProduct(productRequest, completion)
    }

}