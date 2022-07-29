package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ActivityRegistrationKtpactivityBinding
import com.gov.sidesa.ui.registration.RegistrationStackState

class RegistrationKTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationKtpactivityBinding
    private lateinit var viewModel: RegistrationKTPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[RegistrationKTPViewModel::class.java]
        binding = ActivityRegistrationKtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initListener()
        showFragment(BiodataKtpFragment(), RegistrationStackState.KtpBiodata)
    }

    private fun initListener() {
        binding.buttonPreviousKtp.setOnClickListener {
            when (viewModel.registrationStackState.value) {
                is RegistrationStackState.KtpAddress -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpBiodata.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpBiodata) }
                }
                is RegistrationStackState.KtpGeneral -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpAddress.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpAddress) }
                }
                is RegistrationStackState.KtpUpload -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpGeneral.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpGeneral) }
                }
                is RegistrationStackState.KtpReviewKtp -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpUpload.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpUpload) }
                }
            }
        }

        binding.buttonNextKtp.setOnClickListener {
            when (viewModel.registrationStackState.value) {
                is RegistrationStackState.KtpBiodata -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpAddress.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpAddress) }
                        ?: showFragment(AddressKtpFragment(), RegistrationStackState.KtpAddress)
                }
                is RegistrationStackState.KtpAddress -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpGeneral.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpGeneral) }
                        ?: showFragment(GeneralKtpFragment(), RegistrationStackState.KtpGeneral)
                }
                is RegistrationStackState.KtpGeneral -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpUpload.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpUpload) }
                        ?: showFragment(UploadKtpFragment(), RegistrationStackState.KtpUpload)
                }
                is RegistrationStackState.KtpUpload -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpReviewKtp.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpReviewKtp) }
                        ?: showFragment(ReviewKtpFragment(), RegistrationStackState.KtpReviewKtp)
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.registrationStackState.observe(this) { state ->
            when (state) {
                is RegistrationStackState.KtpBiodata -> {
                    binding.progressBar.progress = 10
                    binding.buttonPreviousKtp.visibility = GONE
                }
                is RegistrationStackState.KtpAddress -> {
                    binding.progressBar.progress = 20
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KtpGeneral -> {
                    binding.progressBar.progress = 30
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KtpUpload -> {
                    binding.progressBar.progress = 40
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KtpReviewKtp -> {
                    binding.progressBar.progress = 50
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
            }
        }
    }

    private fun showFragment(clazz: Fragment, state: RegistrationStackState) {
        viewModel.registrationStackState.value = state

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, clazz, state.toString())
            .addToBackStack(state.toString())
            .setReorderingAllowed(true)
            .commit()
    }

}