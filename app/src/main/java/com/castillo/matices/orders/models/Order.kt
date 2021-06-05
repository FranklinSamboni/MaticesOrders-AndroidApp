package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Order(
        @SerializedName("_id")
        @PrimaryKey
        var id: String = "",
        var status: OrderStatus? = null,
        var dateCreated: Long = 0,
        var dateSent: Long = 0,
        var client: Client? = null,
        var products: RealmList<Product>? = null,
        var shipper: String = "",
        var shippingAddress: String = "") : RealmObject(), Parcelable {

    var statusEnumEnum: OrderStatusEnum
        get() {
            return try {
                OrderStatusEnum.valueOf(status?.name ?: "")
            } catch (e: java.lang.Exception) {
                return OrderStatusEnum.RECEIVED
            }
        }
        set(value) {
            status = OrderStatus(value.name)
        }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(OrderStatus::class.java.classLoader),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(Client::class.java.classLoader),
        parcel.readRealmList(),
        parcel.readString()!!
    ) {
    }

    companion object : Parceler<Order> {

        override fun Order.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeParcelable(status, flags)
            parcel.writeLong(dateCreated)
            parcel.writeLong(dateSent)
            parcel.writeParcelable(client, flags)
            parcel.writeRealmList(products)
            parcel.writeString(shipper)
        }

        override fun create(parcel: Parcel): Order {
            return Order(parcel)
        }
    }

}

inline fun <reified T> Parcel.writeRealmList(realmList: RealmList<T>?)
        where T : RealmModel,
              T : Parcelable {
    writeInt(when {
        realmList == null -> 0
        else -> 1
    })
    if (realmList != null) {
        writeInt(realmList.size)
        for (t in realmList) {
            writeParcelable(t, 0)
        }
    }
}

inline fun <reified T> Parcel.readRealmList(): RealmList<T>?
        where T : RealmModel,
              T : Parcelable = when {
    readInt() > 0 -> RealmList<T>().also { list ->
        repeat(readInt()) {
            list.add(readParcelable(T::class.java.classLoader))
        }
    }
    else -> null
}