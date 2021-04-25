package com.castillo.matices.orders.models

class Client(
        var id: String,
        var name: String,
        var lastname: String,
        var identificationNumber: Int?,
        var identificationType: IdentificationType? = null,
        var phoneCode: PhoneCode? = null,
        var phone: String,
        var city: String,
        var address: String
) {
    fun fullname(): String {
        return "$name $lastname"
    }
}