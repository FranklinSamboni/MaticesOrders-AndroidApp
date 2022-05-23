package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Product(
    @SerializedName("_id")
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var size: Size? = null,
    var color: Color? = null,
    var stampRef: Stamp? = null,
    var description: String = "",
    var isStampCutted: Boolean = false,
    var price: Double = 0.0
) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Size::class.java.classLoader),
        parcel.readParcelable(Color::class.java.classLoader),
        parcel.readParcelable(Stamp::class.java.classLoader),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    ) {
    }

    companion object : Parceler<Product> {

        override fun Product.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
            parcel.writeParcelable(size, flags)
            parcel.writeParcelable(color, flags)
            parcel.writeParcelable(stampRef, flags)
            parcel.writeString(description)
            parcel.writeByte(if (isStampCutted) 1 else 0)
            parcel.writeDouble(price)
        }

        override fun create(parcel: Parcel): Product {
            return Product(parcel)
        }
    }

}