package com.castillo.matices.orders.models

class Shirt(var size: Size,
            var color: Color,
            var stamp: String,
            var description: String,
            var isStampCutted: Boolean = false,
            id: String,
            price: Double): Product(id, price) {

    fun getTitle(): String {
        return "Camiseta"
    }
}