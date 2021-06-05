package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class PhoneCode(
        @PrimaryKey
        var code: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    companion object : Parceler<PhoneCode> {

        override fun PhoneCode.write(parcel: Parcel, flags: Int) {
            parcel.writeString(code)
        }

        override fun create(parcel: Parcel): PhoneCode {
            return PhoneCode(parcel)
        }
    }
}
