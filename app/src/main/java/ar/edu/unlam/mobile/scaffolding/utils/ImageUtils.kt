package ar.edu.unlam.mobile.scaffolding.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayInputStream

fun decodeBase64ToBitmap(dataUrl: String): Bitmap? =
    try {
        val base64Part = dataUrl.substringAfter("base64,", "")
        val decodedBytes = Base64.decode(base64Part, Base64.DEFAULT)
        BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
    } catch (_: Exception) {
        null
    }
