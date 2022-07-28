package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ActivityRegistrationKtpactivityBinding

class RegistrationKTPActivity : AppCompatActivity() {

    val TOTAL_PAGE = 14
    var currentPage = 1

    private lateinit var binding: ActivityRegistrationKtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationKtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.progress = 10

        showKtpFragment()
    }

    private fun showKtpFragment() {
        val ktpFragment = BiodataKtpFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment, ktpFragment).commit()
    }
}