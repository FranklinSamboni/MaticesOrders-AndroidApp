package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Size(
        @PrimaryKey
        var name: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    companion object : Parceler<Size> {

        override fun Size.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): Size {
            return Size(parcel)
        }
    }
}
