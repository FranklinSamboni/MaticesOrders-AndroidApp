package com.castillo.matices.orders.data

class DataResponse<T>(
        val statusCode: Int,
        val data: T
)