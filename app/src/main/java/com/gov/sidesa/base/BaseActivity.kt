package com.gov.sidesa.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.ui.widget.LoadingDialogWidget

abstract class BaseActivity : AppCompatActivity() {
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                onResultData(data)
            }
        }

    open fun onResultData(result: Intent?) {}

    var loadingDialog: LoadingDialogWidget? = null

    fun handleLoadingWidget(isLoading: Boolean) {
        if (isLoading) showLoadingWidget() else hideLoadingWidget()
    }

    private fun showLoadingWidget() {
        loadingDialog = LoadingDialogWidget.newInstance()
        loadingDialog?.show(supportFragmentManager, "LOADING_DIALOG")
    }

    private fun hideLoadingWidget() {
        loadingDialog?.dismiss()
    }
}