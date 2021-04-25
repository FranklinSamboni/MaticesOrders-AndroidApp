package com.castillo.matices.orders.models

import android.content.Context
import com.castillo.matices.orders.R

enum class IdentificationType {
    CC
}

fun IdentificationType.getDescription(context: Context) : String {
    return when (this) {
        IdentificationType.CC ->  context.getString(R.string.CC)
    }
}