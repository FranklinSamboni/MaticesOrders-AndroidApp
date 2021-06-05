package com.castillo.matices.orders.viewmodels

import com.castillo.matices.orders.OrdersApp
import com.castillo.matices.orders.R
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.OrderStatusEnum
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel(var order: Order) {

    var productsCount: String
        get() {
            var text = OrdersApp.getContext()?.getString(R.string.products_count)?.format(order.products?.size ?: 0) ?: ""
            return text
        }
        set(value) {}

    var dateSentFormatted: String
        get() {
            if (order.dateSent == 0.toLong()) return ""

            var dateFormat = OrdersApp.getContext()?.getString(R.string.sent_date_format) ?: ""

            val simpleDateFormat = SimpleDateFormat(dateFormat)
            var format = ""
            try {
                val date = Date(order.dateSent)
                format = simpleDateFormat.format(date)
            } catch (e: Exception) {
                format = ""
            }
            return format
        }
        set(value) {}


    var statusDisplayName: String
        get() {
            val context = OrdersApp.getContext()
            return when (order.statusEnumEnum) {
                OrderStatusEnum.RECEIVED -> context?.getString(R.string.order_state_received) ?: ""
                OrderStatusEnum.PROCESSING -> context?.getString(R.string.order_state_processing) ?: ""
                OrderStatusEnum.SENT -> context?.getString(R.string.order_state_sent) ?: ""
                OrderStatusEnum.CLOSED -> context?.getString(R.string.order_state_closed) ?: ""
            }
        }
        set(value) {}

    var shipper: String
        get() {
            return order.shipper
        }
        set(value) {}

    var clientFullName: String
        get() {
            return order.client?.fullName() ?: ""
        }
        set(value) {}

    var clientAddress: String
        get() {
            return order.client?.city + " " + order.client?.address
        }
        set(value) {}

    var clientIdentification: String
        get() {
            return order.client?.identificationType?.id + " " + order.client?.identificationNumber
        }
        set(value) {}

    var clientPhone: String
        get() {
            return order.client?.phone ?: ""
        }
        set(value) {}

}