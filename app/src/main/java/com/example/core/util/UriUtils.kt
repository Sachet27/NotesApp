package com.example.core.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

fun saveImageToStorage(context: Context, sourceUri: Uri): Uri? {
    val resolver: ContentResolver = context.contentResolver

    // Define the directory and filename for the copied image
    val fileName = "note_image_${System.currentTimeMillis()}.jpg"

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // ✅ Save to MediaStore (Recommended for API 29+)
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyNotesApp")
        }
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                resolver.openInputStream(sourceUri)?.copyTo(outputStream)
            }
        }
        imageUri
    } else {
        // ✅ Save to Internal Storage for older Android versions
        val directory = File(context.filesDir, "images")
        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, fileName)
        resolver.openInputStream(sourceUri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        Uri.fromFile(file)
    }
}