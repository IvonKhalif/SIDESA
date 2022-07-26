package com.gov.sidesa.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gov.sidesa.utils.constants.ToastConstant

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                onResultData(data)
            }
        }

    open fun onResultData(result: Intent?) {}

    fun showSuccessMessage(message: String) =
        activity?.let {
            (activity as? BaseActivity)?.customToast(
                it,
                message,
                ToastConstant.CUSTOM_TOAST_SUCCESS
            )
        }

    fun showErrorMessage(message: String) =
        activity?.let {
            (activity as? BaseActivity)?.customToast(
                it,
                message,
                ToastConstant.CUSTOM_TOAST_ERROR
            )
        }

    fun showErrorMessage(throwable: Throwable) =
        activity?.let {
            (activity as? BaseActivity)?.customToast(
                it,
                throwable.message.orEmpty(),
                ToastConstant.CUSTOM_TOAST_ERROR
            )
        }

    fun handleLoadingWidget(isLoading: Boolean) =
        activity?.let {
            (activity as? BaseActivity)?.handleLoadingWidget(
                childFragmentManager,
                isLoading
            )
        }

}