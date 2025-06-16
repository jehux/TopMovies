package com.example.topmovies.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ImageCacheManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveImage(fileName: String, inputStream: InputStream): File? {
        return try {
            val file = File(context.filesDir, fileName)
            file.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isImageCached(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }

    fun getCachedImage(fileName: String): File {
        val file = File(context.filesDir, fileName)
        return file
    }

}
