package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Color(
        @PrimaryKey
        var name: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    companion object : Parceler<Color> {

        override fun Color.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): Color {
            return Color(parcel)
        }
    }

}
