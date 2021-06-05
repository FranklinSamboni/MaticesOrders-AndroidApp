package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

enum class OrderStatusEnum() {
    RECEIVED,
    PROCESSING,
    SENT,
    CLOSED
}

@Parcelize
open class OrderStatus(
        @PrimaryKey
        var name: String = "") : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!
    ) {
    }

    companion object : Parceler<OrderStatus> {

        override fun OrderStatus.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): OrderStatus {
            return OrderStatus(parcel)
        }
    }

}
