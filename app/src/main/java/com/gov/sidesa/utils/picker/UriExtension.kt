package com.gov.sidesa.utils.picker

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.core.content.FileProvider
import com.gov.sidesa.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

fun File.toUriProvider(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        this
    )
}

fun Context.createFile() = File.createTempFile(
    "IMG_${System.currentTimeMillis()}",
    ".jpg",
    cacheDir
).apply {
    createNewFile()
    deleteOnExit()
}

fun Context.createFileUri() = this.createFile().toUriProvider(context = this)

suspend fun Bitmap.asFile(context: Context): File = withContext(Dispatchers.IO)  {
    val file = context.createFile()
    val uri = file.toUriProvider(context)

    try {
        val out = FileOutputStream(file.absoluteFile)
        compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.close()
    } catch (_ : Throwable) {

    }

    file
}

fun String.filePathToBase64(): String {
    val file = File(this)

    return try {
        Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)
    } catch (_ : Throwable) {
        ""
    }
}