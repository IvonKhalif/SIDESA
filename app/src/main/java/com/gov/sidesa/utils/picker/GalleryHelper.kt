package com.gov.sidesa.utils.picker

import android.Manifest
import androidx.activity.result.ActivityResultLauncher

class GalleryHelper {

    companion object {
        private val PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    var onNeedPermission: (permissions: Array<String>) -> Unit = {}

    private lateinit var galleryLauncher: ActivityResultLauncher<String>

    fun open(launcher: ActivityResultLauncher<String>) {
        galleryLauncher = launcher
        onNeedPermission.invoke(PERMISSION)
    }

    private fun showGallery() {
        galleryLauncher.launch("image/*")
    }

    fun onPermissionForResult(permissions: Map<String, Boolean>) {
        // filter permission not granted is empty
        val isAllGranted = permissions.filter { !it.value }.isEmpty()

        if (isAllGranted) {
            showGallery()
        } else {
            // show error
        }
    }
}