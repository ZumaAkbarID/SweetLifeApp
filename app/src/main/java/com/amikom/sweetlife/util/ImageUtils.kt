package com.amikom.sweetlife.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
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

private fun saveImageToFile(context: Context, image: ImageProxy): File {
    // Menyimpan file di direktori cache aplikasi
    val file = File(context.cacheDir, "scan_result_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)

    try {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)

        outputStream.write(bytes)
        outputStream.flush()
        outputStream.close()
    } catch (e: Exception) {
        Log.e("CameraScreen", "Error saving image to file", e)
    }

    return file
}
