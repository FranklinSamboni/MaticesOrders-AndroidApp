package com.castillo.matices.orders.sections.home

import com.castillo.matices.orders.data.repositories.OrderRepository
import com.castillo.matices.orders.models.*

class HomeViewModel {

    private val repository = OrderRepository()

    fun getOrders(completion: (list: List<Order>) -> Unit) {
        return repository.getOrders(completion)
    }

}