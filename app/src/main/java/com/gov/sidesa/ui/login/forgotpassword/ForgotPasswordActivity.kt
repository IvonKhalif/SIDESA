package com.gov.sidesa.ui.login.forgotpassword

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityForgotPasswordBinding
import com.gov.sidesa.utils.enums.CategoryLetterEnum
import com.gov.sidesa.utils.extension.isEmailPattern

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.input_password_forgot_password_label)
                setNavigationOnClickListener { finish() }
            }
            inputEmail.value().doOnTextChanged { text, _, _, _ ->
                buttonSendEmail.isEnabled = text.toString().isEmailPattern()
            }
        }
    }
}