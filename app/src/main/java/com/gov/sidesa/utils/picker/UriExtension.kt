package com.gov.sidesa.utils.picker

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.gov.sidesa.BuildConfig
import java.io.File

fun File.toUriProvider(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        this
    )
}