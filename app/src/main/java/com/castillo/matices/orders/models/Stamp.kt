package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Stamp(
    @PrimaryKey
    @SerializedName("_id")
    var id: String = "",
    var name: String = "",
    var image: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    fun getFullURL(): String {
        return "https://cdn.maticescolombia.com/" + image
    }

    companion object : Parceler<Stamp> {
        override fun Stamp.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
            parcel.writeString(image)
        }

        override fun create(parcel: Parcel): Stamp {
            return Stamp(parcel)
        }
    }
}
