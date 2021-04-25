package com.castillo.matices.orders.models

import android.content.Context
import com.castillo.matices.orders.R

enum class OrderState {
    RECEIVED,
    PROCESSING,
    SENT,
    CLOSED
}

fun OrderState.getDescription(context: Context) : String {
    return when (this) {
        OrderState.RECEIVED ->  context.getString(R.string.black)
        OrderState.PROCESSING -> context.getString(R.string.white)
        OrderState.SENT -> context.getString(R.string.white)
        OrderState.CLOSED -> context.getString(R.string.white)
    }
}