package com.gov.sidesa.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

abstract class BaseActivity : AppCompatActivity() {

    var loadingDialog: LoadingDialogWidget? = null

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
        loadingDialog?.dismiss()
    }
}