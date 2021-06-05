package com.castillo.matices.orders

import android.app.Application
import android.content.Context
import com.castillo.matices.orders.OrdersApp.Companion.mContext
import io.realm.Realm

class OrdersApp: Application() {

    companion object {
        private var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this;
        Realm.init(this)
    }

}