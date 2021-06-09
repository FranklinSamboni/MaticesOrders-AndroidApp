package com.castillo.matices.orders.data

import com.castillo.matices.orders.models.*
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.annotations.PrimaryKey

class OrderRequest(val order: Order) {
    @SerializedName("_id")
    var id: String = order.id
    var status: String = order.status?.name ?: ""
    var dateSent: Long = order.dateSent
    var clientId: String = order.client?.id ?: ""
    var shipper: String = order.shipper
    var shippingAddress: String = order.shippingAddress
}

class ClientRequest(client: Client) {
    @SerializedName("_id")
    var id = client.id
    var name = client.name
    var lastName = client.lastName
    var identificationNumber = client.identificationNumber
    var identificationType = client.identificationType?.id ?: ""
    var phone = client.phone
    var phoneCode = client.phoneCode?.code ?: ""
    var city = client.city
    var address = client.address
}

class ProductRequest(val product: Product) {
    @SerializedName("_id")
    var id = product.id
    var name = product.name
    var size = product.size?.name ?: ""
    var color = product.color?.name ?: ""
    var stamp = product.stamp
    var description = product.description
    var isStampCutted = product.isStampCutted
    var price = product.price
}

class AddProductToOrderRequest(order: Order, product: Product) {
    var orderId = order.id
    var productId = product.id
}

class StampRequest(val stamp: Stamp, image64: String) {
    @SerializedName("_id")
    var id = stamp.id
    var name = stamp.name
    var imageBase64 = image64
}


