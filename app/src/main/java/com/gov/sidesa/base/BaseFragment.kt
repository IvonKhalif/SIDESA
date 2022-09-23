package com.gov.sidesa.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gov.sidesa.utils.constants.ToastConstant

abstract class BaseFragment: Fragment() {

    var loadingDialog: LoadingDialogWidget? = null

    protected val baseActivity: BaseActivity
        get() = requireActivity() as BaseActivity

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
        activity?.let { (activity as? BaseActivity)?.customToast(it, message, ToastConstant.CUSTOM_TOAST_SUCCESS) }

    fun showErrorMessage(message: String) =
        activity?.let { (activity as? BaseActivity)?.customToast(it, message, ToastConstant.CUSTOM_TOAST_ERROR) }

    fun showErrorMessage(throwable: Throwable) =
        activity?.let { (activity as? BaseActivity)?.customToast(it, throwable.message.orEmpty(), ToastConstant.CUSTOM_TOAST_ERROR) }

    fun handleLoadingWidget(fragmentManager: FragmentManager, isLoading: Boolean) {
        if (isLoading) showLoadingWidget(fragmentManager) else hideLoadingWidget()
    }

    private fun showLoadingWidget(fragmentManager: FragmentManager) {
        showImmediately(fragmentManager, "loading") {
            loadingDialog = LoadingDialogWidget.newInstance()
            loadingDialog!!
        }
    }

    private fun hideLoadingWidget() {
        loadingDialog?.dismissAllowingStateLoss()
    }

}