package com.castillo.matices.orders.sections.home

import com.castillo.matices.orders.data.repositories.OrderRepository
import com.castillo.matices.orders.models.*

class OrderListViewModel {

    private val repository = OrderRepository()

    fun getOrders(completion: (list: List<Order>, error: String) -> Unit) {
        return repository.getOrders(completion)
    }

}