package com.castillo.matices.orders.sections.home

import com.castillo.matices.orders.data.repositories.StampRepository
import com.castillo.matices.orders.models.Stamp

class StampListViewModel {

    private val repository = StampRepository()

    fun getStamps(completion: (stamps: List<Stamp>, error: String) -> Unit) {
        repository.getStamps(completion)
    }
}