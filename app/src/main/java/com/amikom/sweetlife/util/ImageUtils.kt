package com.amikom.sweetlife.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun bitmapToFile(context: Context, bitmap: Bitmap, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    FileOutputStream(file).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
    }
    return file
}