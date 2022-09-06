package com.gov.sidesa.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gov.sidesa.utils.constants.ToastConstant

abstract class BaseFragment: Fragment() {

    var loadingDialog: LoadingDialogWidget? = null

    protected val baseActivity: com.gov.sidesa.base.BaseActivity
        get() = requireActivity() as com.gov.sidesa.base.BaseActivity

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                onResultData(data)
            }
        }

    open fun onResultData(result: Intent?) {}

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

    fun showErrorMessage(message: String) =
        baseActivity.customToast(baseActivity, message, ToastConstant.CUSTOM_TOAST_ERROR)

}