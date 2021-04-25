package com.castillo.matices.orders.models

import android.content.Context
import com.castillo.matices.orders.R

enum class Color {
    Black,
    White
}

fun Color.getDescription(context: Context) : String {
    return when (this) {
        Color.Black ->  context.getString(
            R.string.black
        )
        Color.White -> context.getString(
            R.string.white
        )
    }
}