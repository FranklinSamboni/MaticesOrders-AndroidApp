package com.castillo.matices.orders.data.responses

import android.os.Parcelable
import com.castillo.matices.orders.models.Color
import com.castillo.matices.orders.models.Size
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class ProductDeleted(
        @SerializedName("_id")
        var id: String = "",
        var name: String = "",
        var size: String = "",
        var color: String = "",
        var stamp: String = "",
        var description: String = "",
        var isStampCutted: Boolean = false,
        var price: Double = 0.0) {
}