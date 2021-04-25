package com.castillo.matices.orders.models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Order(
        var id: String,
        var state: OrderState,
        var dateCreated: String,
        var dateSent: String,
        var client: Client,
        var products: List<Product>,
        var shipper: String
) {
    fun dateCreatedFormatted(): String {
        val simpleDateFormat = SimpleDateFormat("d 'de' MMMM yyyy")
        var format = ""
        try {
            val date = Date(dateCreated.toLong())
            format = simpleDateFormat.format(date)
        } catch (e: Exception) {
            format = ""
        }
        return format
    }
}