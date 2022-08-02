package com.gov.sidesa.utils.picker

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.result.ActivityResultLauncher


class CameraHelper(private val context: Context) {

    companion object {
        private val PERMISSION = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        } else {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }

    var uri: Uri? = null
        private set

    var onNeedPermission: (permissions: Array<String>) -> Unit = {}

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    fun open(launcher: ActivityResultLauncher<Uri>) {
        cameraLauncher = launcher

        onNeedPermission.invoke(PERMISSION)
    }

    private fun showCamera() {
        context.createFileUri()
            .apply {
                uri = this
            }
            .run {
                cameraLauncher.launch(this)
            }
    }

    fun onPermissionForResult(permissions: Map<String, Boolean>) {
        // filter permission not granted is empty
        val isAllGranted = permissions.filter { !it.value }.isEmpty()

        if (isAllGranted) {
            showCamera()
        } else {
            // show error
        }
    }
}