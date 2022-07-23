package com.gov.sidesa.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityLoginBinding
import com.gov.sidesa.ui.DashboardActivity
import com.gov.sidesa.ui.login.password.PasswordActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        binding.apply {
            buttonLogin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, PasswordActivity::class.java))
            }

            inputUserKtp.value().doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && text.length < 16) {
                    inputUserKtp.inputLayout().error = getString(R.string.login_ktp_number_error_16_digit)
                } else {
                    buttonLogin.isEnabled = true
                    inputUserKtp.inputLayout().error = null
                    inputUserKtp.inputLayout().isErrorEnabled = false
                }
            }
        }
    }
}