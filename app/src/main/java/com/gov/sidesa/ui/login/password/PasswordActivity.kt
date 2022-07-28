package com.gov.sidesa.ui.login.password

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.databinding.ActivityLoginBinding
import com.gov.sidesa.databinding.ActivityPasswordBinding
import com.gov.sidesa.ui.DashboardActivity
import com.gov.sidesa.ui.login.LoginViewModel
import com.gov.sidesa.ui.login.forgotpassword.ForgotPasswordActivity
import com.gov.sidesa.utils.constants.UserExtrasConstant
import com.gov.sidesa.utils.enums.StatusResponseEnum
import com.gov.sidesa.utils.extension.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private val viewModel: PasswordViewModel by viewModel()
    private val userNik by lazy {
        intent.getStringExtra(UserExtrasConstant.EXTRA_USER_NIK).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            var titleToolbar = ""
            when {
                accountHasRegistered() -> {
                    titleToolbar = getString(R.string.login_title)
                    buttonLogin.text = getString(R.string.login_title)
                    buttonForgotPassword.isVisible = true
                    inputRePassword.isVisible = false
                }
                accountHasReset() -> {
                    titleToolbar = getString(R.string.login_new_password_title)
                    textInputPasswordHeader.text =
                        getString(R.string.input_password_has_reseted_header)
                    textInputPasswordFooter.isVisible = false
                }
                else -> {
                    titleToolbar = getString(R.string.input_password_create_password_label)
                    textInputPasswordHeader.text =
                        getString(R.string.input_password_has_not_account_header)
                    textInputPasswordFooter.isVisible = false
                }
            }
            customToolbar.toolbarDetailProfile.apply {
                title = titleToolbar
                navigationIcon =
                    ContextCompat.getDrawable(this@PasswordActivity, R.drawable.ic_crossline_24dp)
                setNavigationOnClickListener { finish() }
            }
            inputPassword.value().doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank() && text.length < 7) {
                    inputPassword.inputLayout().error =
                        getString(R.string.input_password_error_7_digit)
                    buttonLogin.isEnabled = false
                } else {
                    buttonLogin.isEnabled = !text.isNullOrBlank()
                    inputPassword.inputLayout().error = null
                    inputPassword.inputLayout().isErrorEnabled = false
                }
            }
            inputPassword.value().setOnFocusChangeListener { _, isFocus ->
                inputPassword.inputLayout().hint = getString(R.string.login_password_label)
            }
            inputRePassword.value().doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && text != inputPassword.value().text) {
                    inputRePassword.inputLayout().error =
                        getString(R.string.input_password_error_not_match)
                    buttonLogin.isEnabled = false
                } else {
                    buttonLogin.isEnabled = true
                    inputRePassword.inputLayout().error = null
                    inputRePassword.inputLayout().isErrorEnabled = false
                }
            }

            buttonLogin.setOnClickListener {
                if (accountHasRegistered())
                    viewModel.login(userNik, inputPassword.text())
                else
                    viewModel.createPassword(userNik, inputPassword.text())
            }

            buttonForgotPassword.setOnClickListener {
                startActivity(Intent(this@PasswordActivity, ForgotPasswordActivity::class.java))
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            userLiveData.observeNonNull(this@PasswordActivity, ::handleUpdateUser)
            statusCreatePassword.observeNonNull(this@PasswordActivity, ::handleStatusCreated)
            loadingWidgetLiveData.observeNonNull(this@PasswordActivity, ::handleLoadingWidget)
        }
    }

    private fun handleStatusCreated(status: String) {
        if (status == StatusResponseEnum.SUCCESS.status)
            viewModel.login(userNik, binding.inputPassword.text())
    }

    private fun handleUpdateUser(user: User) {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun accountHasRegistered() =
        intent.getStringExtra(UserExtrasConstant.EXTRA_STATUS_NIK) == StatusResponseEnum.REGISTERED.status

    private fun accountHasReset() =
        intent.getStringExtra(UserExtrasConstant.EXTRA_STATUS_NIK) == StatusResponseEnum.RESET_PASSWORD.status
}