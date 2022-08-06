package com.gov.sidesa.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.ui.dashboard.DashboardActivity
import com.gov.sidesa.ui.login.LoginActivity
import com.gov.sidesa.utils.DateUtil
import com.gov.sidesa.utils.PreferenceUtils
import org.threeten.bp.LocalDate

class SplashActivity : BaseActivity() {
    private val user by lazy {
        PreferenceUtils.getAccount()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null)
                checkExpiredDate()
            else
                goToLogin()
        }, 2000)
    }

    private fun checkExpiredDate() {
        val expiredDateLeft = DateUtil.expiredDateLeft(user?.expiredDate ?: LocalDate.now().toString())
        if (expiredDateLeft >= 0) goToMainActivity() else goToLogin()
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}