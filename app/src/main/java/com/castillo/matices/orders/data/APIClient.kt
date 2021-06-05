package com.castillo.matices.orders.data

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    private val BASE_URL = "http://10.0.2.2:3000/matices/api/v1/"

    private var retrofit: Retrofit
    fun getService(): APIService {
        return retrofit.create(APIService::class.java)
    }

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}