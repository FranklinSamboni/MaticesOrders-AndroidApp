package com.castillo.matices.orders.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
open class Client(
        @SerializedName("_id")
        @PrimaryKey
        var id: String = "",
        var name: String = "",
        var lastName: String = "",
        var identificationNumber: String = "",
        var identificationType: IdentificationType? = null,
        var phoneCode: PhoneCode? = null,
        var phone: String = "",
        var city: String = "",
        var address: String = ""): RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readParcelable(IdentificationType::class.java.classLoader),
            parcel.readParcelable(PhoneCode::class.java.classLoader),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    companion object : Parceler<Client> {

        override fun Client.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
            parcel.writeString(lastName)
            parcel.writeString(identificationNumber)
            parcel.writeParcelable(identificationType, flags)
            parcel.writeParcelable(phoneCode, flags)
            parcel.writeString(phone)
            parcel.writeString(city)
            parcel.writeString(address)
        }

        override fun create(parcel: Parcel): Client {
            return Client(parcel)
        }
    }


    fun fullName(): String {
        return "$name $lastName"
    }
}