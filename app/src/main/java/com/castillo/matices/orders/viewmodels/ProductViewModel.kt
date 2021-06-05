package com.castillo.matices.orders.viewmodels

import com.castillo.matices.orders.models.Color
import com.castillo.matices.orders.models.Product
import com.castillo.matices.orders.models.Size
import java.text.NumberFormat
import java.util.*

class ProductViewModel(var product: Product) {

    var name: String
        get() {
            return product.name
        }
        set(value) { product.name = value }

    var price: String
        get() {
            if (product.price == 0.0) {
                return ""
            } else {
                return product.price.toLong().toString()
            }
        }
        set(value) {
            product.price = try {
                value.toDouble()
            } catch (e: Exception) {
                0.0
            }
        }

    var formattedPrice: String
        get() {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            val locale = Locale("es","CO")
            format.currency = Currency.getInstance(locale)

            return format.format(product.price)
        }
        set(value) {}

    var stamp: String
        get() {
            return product.stamp
        }
        set(value) { product.stamp = value }

    var description: String
        get() {
            return product.description
        }
        set(value) { product.description = value }

    var size: Size?
        get() {
            return product.size
        }
        set(value) { product.size = value }

    var color: Color?
        get() {
            return product.color
        }
        set(value) { product.color = value }

    var isStampCutted: Boolean
        get() {
            return product.isStampCutted
        }
        set(value) { product.isStampCutted = value }

    fun getSizeDisplayText(): String {
        return "Talla: ${product.size?.name}"
    }

    fun getColorDisplayText(): String {
        return "Color: ${product.color?.name}"
    }
}