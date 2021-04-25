package com.castillo.matices.orders.models


enum class PhoneCode {
    COP
}

fun PhoneCode.getDescription() : String {
    return when (this) {
        PhoneCode.COP -> "+57"
    }
}