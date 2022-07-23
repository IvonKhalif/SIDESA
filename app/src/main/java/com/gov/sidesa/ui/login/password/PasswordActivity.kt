package com.gov.sidesa.ui.login.password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            if (accountHasRegistered()) {
                buttonForgotPassword.isVisible = true
                inputRePassword.isVisible = false
            }

            inputPassword.value().doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && text.length < 7) {
                    inputPassword.inputLayout().error =
                        getString(R.string.input_password_error_7_digit)
                    buttonLogin.isEnabled = false
                } else {
                    buttonLogin.isEnabled = !text.isNullOrBlank()
                    inputPassword.inputLayout().error = null
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
        }
    }

    private fun handleStatusCreated(status: String) {
        if (status == StatusResponseEnum.SUCCESS.status)
            viewModel.login(userNik, binding.inputPassword.text())
    }

    private fun handleUpdateUser(user: User) {
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    private fun accountHasRegistered() =
        intent.getStringExtra(UserExtrasConstant.EXTRA_STATUS_NIK) == StatusResponseEnum.REGISTERED.status
}