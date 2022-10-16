package com.gov.sidesa.ui.login.password

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.databinding.ActivityPasswordBinding
import com.gov.sidesa.ui.login.forgotpassword.ForgotPasswordActivity
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.PreferenceUtils.USER_RESPONSE_PREFERENCE
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
    private val userId by lazy {
        intent.getLongExtra(UserExtrasConstant.EXTRA_USER_ID, 0)
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
                    if (inputRePassword.isVisible)
                        buttonLogin.isEnabled =
                            !text.isNullOrBlank() &&
                                    text.toString() == inputRePassword.value().text.toString() &&
                                    inputRePassword.value().text.isNotBlank()
                    else
                        buttonLogin.isEnabled = !text.isNullOrBlank()
                    inputPassword.inputLayout().error = null
                    inputPassword.inputLayout().isErrorEnabled = false
                    checkRePassword(text.toString(), inputRePassword.value().text.toString())
                }
            }
            inputPassword.value().setOnFocusChangeListener { _, isFocus ->
                inputPassword.inputLayout().hint = getString(R.string.login_password_label)
            }
            inputRePassword.value().doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && text.length < 7) {
                    buttonLogin.isEnabled = false
                } else {
                    buttonLogin.isEnabled = text.toString() == inputPassword.value().text.toString()
                }
                checkRePassword(inputPassword.value().text.toString(), text.toString())
            }

            buttonLogin.setOnClickListener {
                when {
                    accountHasRegistered() -> viewModel.login(userNik, inputPassword.text())
                    accountHasReset() -> viewModel.resetPassword(userId.toString(), inputPassword.text())
                    else -> viewModel.createPassword(userNik, inputPassword.text())
                }
            }

            buttonForgotPassword.setOnClickListener {
                startActivity(Intent(this@PasswordActivity, ForgotPasswordActivity::class.java))
            }
        }

        initObserver()
    }

    private fun ActivityPasswordBinding.checkRePassword(password: String?, rePassword: String?) {
        inputRePassword.inputLayout().isErrorEnabled = !rePassword.isNullOrBlank() &&
                rePassword != password
        if (inputRePassword.inputLayout().isErrorEnabled)
            inputRePassword.inputLayout().error =
                getString(R.string.input_password_error_not_match)
    }

    private fun initObserver() {
        viewModel.apply {
//            userResponseLiveData.observeNonNull(this@PasswordActivity, ::handleUpdateUser)
            statusCreatePassword.observeNonNull(this@PasswordActivity, ::handleStatusCreated)
            loadingWidgetLiveData.observeNonNull(this@PasswordActivity, ::handleLoadingWidget)
            viewModel.closeScreenView.observe(this@PasswordActivity) {
                doOnSuccessLogin()
            }
            serverErrorState.observe(this@PasswordActivity) {
                showErrorMessage(message = it.message.orEmpty())
            }
        }
    }

    private fun handleStatusCreated(status: String) {
        if (status == StatusResponseEnum.SUCCESS.status || status == StatusResponseEnum.RESET_PASSWORD_SUCCESS.status)
            viewModel.login(userNik, binding.inputPassword.text())
    }

    private fun handleUpdateUser(userResponse: UserResponse) {
        PreferenceUtils.put(userResponse, USER_RESPONSE_PREFERENCE)
    }

    private fun doOnSuccessLogin() {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun accountHasRegistered() =
        intent.getStringExtra(UserExtrasConstant.EXTRA_STATUS_NIK) == StatusResponseEnum.REGISTERED.status

    private fun accountHasReset() =
        intent.getStringExtra(UserExtrasConstant.EXTRA_STATUS_NIK) == StatusResponseEnum.RESET_PASSWORD.status
}