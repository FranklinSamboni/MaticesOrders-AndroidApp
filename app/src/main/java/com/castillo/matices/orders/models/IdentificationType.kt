package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class IdentificationType(
        @SerializedName("typeId")
        @PrimaryKey
        var id: String = "",
        var name: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    companion object : Parceler<IdentificationType> {

        override fun IdentificationType.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): IdentificationType {
            return IdentificationType(parcel)
        }
    }
}

