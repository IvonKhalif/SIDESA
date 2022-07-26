package com.gov.sidesa.utils.picker

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.gov.sidesa.base.BaseActivity
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.launch
import java.io.File

class SelectImageBottomSheet : MenuIconWithHeadlineBottomSheet<SelectImageMenuType>() {

    // Camera helper
    private val cameraHelper by lazy { CameraHelper(requireContext()) }

    // Camera Launcher
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { selected ->
        if (selected) {
            onImageSelected(cameraHelper.uri)
        }
    }

    // Camera Permission Launcher
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions -> cameraHelper.onPermissionForResult(permissions = permissions) }

    // Gallery helper
    private val galleryHelper by lazy { GalleryHelper() }

    // Gallery Launcher
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> onImageSelected(uri) }

    // Gallery Permission Launcher
    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions -> galleryHelper.onPermissionForResult(permissions = permissions) }

    var onImageSelected: (File) -> Unit = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
    }

    private fun initView() {
        binding.tvTitle.text = "Pilih Media"

        adapter = MenuIconWithHeadlineAdapter<SelectImageMenuType>(
            setHeadline = { getString(it.title) },
            setIconLeft = { it.icon },
            areItemsTheSame = { old, new -> old == new },
            areContentTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
            onClick = {
                when (it) {
                    is SelectImageMenuType.Gallery -> galleryHelper.open(launcher = galleryLauncher)
                    is SelectImageMenuType.Camera -> cameraHelper.open(launcher = cameraLauncher)
                }
            }
        ).also {
            it.submitList(
                listOf(
                    SelectImageMenuType.Camera,
                    SelectImageMenuType.Gallery
                )
            )
        }

        binding.menuList.adapter = adapter
    }

    private fun initEvent() {
        cameraHelper.onNeedPermission = {
            cameraPermissionLauncher.launch(it)
        }

        galleryHelper.onNeedPermission = {
            galleryPermissionLauncher.launch(it)
        }
    }

    private fun onImageSelected(uri: Uri?) {
        if (uri == null) return

        showLoading(true)

        lifecycleScope.launch {
            val file = try {
                FileUtil.from(requireContext(), uri)
            } catch (e: Exception) {
                null
            }

            if (file != null) {
                val result = Compressor.compress(
                    context = requireContext(),
                    imageFile = file
                ) {
                    resolution(1280, 720)
                    quality(80)
                    size(1_048_576) // 1 MB
                }

                showLoading(false)
                // result can bigger from original
                if (file.length() < result.length()) {
                    onImageSelected.invoke(file)
                } else {
                    onImageSelected.invoke(result)
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        (requireActivity() as? BaseActivity)?.handleLoadingWidget(childFragmentManager, show)
    }

    companion object {
        fun newInstance() = SelectImageBottomSheet()
    }
}