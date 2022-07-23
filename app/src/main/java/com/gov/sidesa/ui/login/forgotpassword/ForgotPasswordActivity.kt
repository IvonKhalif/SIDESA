package com.gov.sidesa.ui.login.forgotpassword

import android.os.Bundle
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityForgotPasswordBinding
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
            val textEmail = binding.inputEmail.value().text
            buttonSendEmail.isEnabled = textEmail.isEmailPattern()
        }
    }
}