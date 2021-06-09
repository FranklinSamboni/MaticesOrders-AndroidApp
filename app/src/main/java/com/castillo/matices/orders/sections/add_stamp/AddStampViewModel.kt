package com.castillo.matices.orders.sections.add_stamp

import android.graphics.Bitmap
import android.util.Base64
import com.castillo.matices.orders.data.AddProductToOrderRequest
import com.castillo.matices.orders.data.ProductRequest
import com.castillo.matices.orders.data.StampRequest
import com.castillo.matices.orders.data.repositories.StampRepository
import com.castillo.matices.orders.models.Stamp
import java.io.ByteArrayOutputStream

class AddStampViewModel {

    private val stampRepository = StampRepository()

    fun saveStamp(stamp: Stamp, bitmap: Bitmap, completion: (success: Boolean, error: String) -> Unit) {

        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val byteArray = byteStream.toByteArray()
        val baseString: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

        val request = StampRequest(stamp, baseString)
        if (stamp.id.isEmpty()) {
            stampRepository.createStamp(request, completion)
        } else {
            stampRepository.updateStamp(request, completion)
        }
    }

    fun deleteStamp(stamp: Stamp, completion: (success: Boolean, error: String) -> Unit) {
        val request = StampRequest(stamp, "")
        stampRepository.deleteStamp(request, completion)
    }
}