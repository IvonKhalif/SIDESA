package com.gov.sidesa.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityLoginBinding
import com.gov.sidesa.ui.dashboard.DashboardActivity
import com.gov.sidesa.ui.login.password.PasswordActivity
import com.gov.sidesa.utils.constants.UserExtrasConstant.EXTRA_STATUS_NIK
import com.gov.sidesa.utils.constants.UserExtrasConstant.EXTRA_USER_ID
import com.gov.sidesa.utils.constants.UserExtrasConstant.EXTRA_USER_NIK
import com.gov.sidesa.utils.extension.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        binding.apply {
            buttonLogin.setOnClickListener {
                viewModel.validateNIK(inputUserKtp.value().text.toString())
            }

            inputUserKtp.value().doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && text.length < 16) {
                    inputUserKtp.inputLayout().error =
                        getString(R.string.login_ktp_number_error_16_digit)
                    buttonLogin.isEnabled = false
                } else {
                    buttonLogin.isEnabled = true
                    inputUserKtp.inputLayout().error = null
                    inputUserKtp.inputLayout().isErrorEnabled = false
                }
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            statusNIKLiveData.observeNonNull(this@LoginActivity, ::handleStatusNIK)
            loadingWidgetLiveData.observeNonNull(this@LoginActivity, ::handleLoadingWidget)
        }
    }

    private fun handleStatusNIK(status: String) {
        val intent = Intent(this, PasswordActivity::class.java)
        intent.putExtra(EXTRA_STATUS_NIK, status)
        intent.putExtra(EXTRA_USER_NIK, binding.inputUserKtp.value().text.toString())
        intent.putExtra(EXTRA_USER_ID, viewModel.userResponseLiveData.value?.id.orEmpty())
        resultLauncher.launch(intent)
    }

    override fun onResultData(result: Intent?) {
        super.onResultData(result)
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}