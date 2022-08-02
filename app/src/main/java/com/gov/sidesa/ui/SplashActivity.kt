package com.gov.sidesa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gov.sidesa.R
import com.gov.sidesa.ui.dashboard.DashboardActivity
import com.gov.sidesa.ui.login.LoginActivity
import com.gov.sidesa.ui.profile.ProfileActivity
import com.gov.sidesa.utils.DateUtil
import com.gov.sidesa.utils.PreferenceUtils
import org.threeten.bp.LocalDateTime

class SplashActivity : AppCompatActivity() {
    private val user by lazy {
        PreferenceUtils.getUser()
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
        val expiredDate = DateUtil.formatStringToDateTime(user?.expiredDate)
        val expiredDateLeft = DateUtil.expiredDateLeft(expiredDate ?: LocalDateTime.now())
        if (expiredDateLeft >= 0) goToLogin() else goToMainActivity()
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }
}