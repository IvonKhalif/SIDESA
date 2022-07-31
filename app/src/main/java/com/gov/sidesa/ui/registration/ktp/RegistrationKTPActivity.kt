package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ActivityRegistrationKtpactivityBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.keluarga.*
import com.gov.sidesa.ui.registration.kk.KkAddressFragment
import com.gov.sidesa.ui.registration.kk.KkBiodataFragment
import com.gov.sidesa.ui.registration.kk.ReviewKkFragment
import com.gov.sidesa.ui.registration.kk.UploadKkFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.gov.sidesa.ui.registration.keluarga.*
import com.gov.sidesa.ui.registration.kk.KkAddressFragment
import com.gov.sidesa.ui.registration.kk.KkBiodataFragment
import com.gov.sidesa.ui.registration.kk.KkDetailFragment
import com.gov.sidesa.ui.registration.kk.UploadKkFragment

class RegistrationKTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationKtpactivityBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationKtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customToolbar.toolbarDetailProfile.title = "Registrasi Data"
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
                is RegistrationStackState.KkBiodata -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KtpReviewKtp.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KtpReviewKtp) }
                }
                is RegistrationStackState.KkAddress -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkBiodata.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkBiodata) }
                }
                is RegistrationStackState.KkUpload -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkAddress.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkAddress) }
                }
                is RegistrationStackState.KkReview -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkUpload.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkUpload) }
                }
                is RegistrationStackState.FamilyFather -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkReview.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkReview) }
                }
                is RegistrationStackState.FamilyMother -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyFather.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyFather) }
                }
                is RegistrationStackState.FamilyApplicant -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyMother.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyMother) }
                }
                is RegistrationStackState.FamilyChild -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyApplicant.toString())
                    fragment?.let { it1 ->
                        showFragment(
                            it1,
                            RegistrationStackState.FamilyApplicant
                        )
                    }
                }
                is RegistrationStackState.FamilyReview -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyChild.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyChild) }
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
                is RegistrationStackState.KtpReviewKtp -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkBiodata.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkBiodata) }
                        ?: showFragment(KkBiodataFragment(), RegistrationStackState.KkBiodata)
                }
                is RegistrationStackState.KkBiodata -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkAddress.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkAddress) }
                        ?: showFragment(KkAddressFragment(), RegistrationStackState.KkAddress)
                }
                is RegistrationStackState.KkAddress -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkUpload.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkUpload) }
                        ?: showFragment(UploadKkFragment(), RegistrationStackState.KkUpload)
                }
                is RegistrationStackState.KkUpload -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.KkReview.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.KkReview) }
                        ?: showFragment(ReviewKkFragment(), RegistrationStackState.KkReview)
                }
                is RegistrationStackState.KkReview -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyFather.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyFather) }
                        ?: showFragment(FamilyFatherFragment(), RegistrationStackState.FamilyFather)
                }
                is RegistrationStackState.FamilyFather -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyMother.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyMother) }
                        ?: showFragment(FamilyMotherFragment(), RegistrationStackState.FamilyMother)
                }
                is RegistrationStackState.FamilyMother -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyApplicant.toString())
                    fragment?.let { it1 ->
                        showFragment(
                            it1,
                            RegistrationStackState.FamilyApplicant
                        )
                    }
                        ?: showFragment(
                            FamilyApplicantFragment(),
                            RegistrationStackState.FamilyApplicant
                        )
                }
                is RegistrationStackState.FamilyApplicant -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyChild.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyChild) }
                        ?: showFragment(FamilyChildFragment(), RegistrationStackState.FamilyChild)
                }
                is RegistrationStackState.FamilyChild -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(RegistrationStackState.FamilyReview.toString())
                    fragment?.let { it1 -> showFragment(it1, RegistrationStackState.FamilyReview) }
                        ?: showFragment(FamilyReviewFragment(), RegistrationStackState.FamilyReview)
                }
                is RegistrationStackState.FamilyReview -> {
                    lifecycleScope.launch {
                        viewModel.registrationNewAccount(this@RegistrationKTPActivity)
                    }
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.registrationStatus.observe(this) {
            it
        }

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
                is RegistrationStackState.KkBiodata -> {
                    binding.progressBar.progress = 55
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KkAddress -> {
                    binding.progressBar.progress = 60
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KkUpload -> {
                    binding.progressBar.progress = 65
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.KkReview -> {
                    binding.progressBar.progress = 70
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.FamilyFather -> {
                    binding.progressBar.progress = 75
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.FamilyMother -> {
                    binding.progressBar.progress = 80
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.FamilyApplicant -> {
                    binding.progressBar.progress = 85
                    binding.buttonPreviousKtp.visibility = VISIBLE
                }
                is RegistrationStackState.FamilyChild -> {
                    binding.progressBar.progress = 95
                    binding.buttonPreviousKtp.visibility = VISIBLE
                    binding.buttonNextKtp.text = "Simpan Data"
                }
                is RegistrationStackState.FamilyReview -> {
                    binding.progressBar.progress = 100
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