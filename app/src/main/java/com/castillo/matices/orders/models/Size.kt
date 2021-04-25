package com.castillo.matices.orders.models

enum class Size {
    S,
    M
}

fun Size.getDescription() : String {
    return when (this) {
        Size.S ->  "S"
        Size.M ->  "M"
    }
}