package com.castillo.matices.orders.data.responses

import com.google.gson.annotations.SerializedName

class OrderDeleted(
        @SerializedName("_id")
        var id: String = "",
        var status: String = "",
        var dateCreated: Long = 0,
        var dateSent: Long = 0,
        var client: String = "",
        var products: List<String>,
        var shipper: String = "",
        var shippingAddress: String = "") {
}