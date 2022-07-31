package com.gov.sidesa.ui.profile.detail.ktp

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailKtpprofileBinding
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.ui.profile.edit.EditProfileKTPActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProfileKTPActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailKtpprofileBinding

    private val viewModel by viewModel<DetailProfileKTPViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKtpprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@DetailProfileKTPActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@DetailProfileKTPActivity) {
            showErrorMessage(throwable = it)
            finish()
        }

        serverErrorState.observe(this@DetailProfileKTPActivity) {
            showErrorMessage(message = it.status.orEmpty())
            finish()
        }

        profileData.observe(this@DetailProfileKTPActivity) {
            assignDataToUi(data = it)
        }
    }

    private fun initView() = with(binding) {
        customToolbar.toolbarDetailProfile.setTitle(R.string.profile_ktp)
    }

    private fun initEvent() = with(binding) {
        binding.buttonEditKtp.setOnClickListener {
            val intent = Intent(this@DetailProfileKTPActivity, EditProfileKTPActivity::class.java)

            resultLauncher.launch(intent)
        }

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun assignDataToUi(data: AccountUiModel) = with(binding) {
        textNik.text = data.kk
        textName.text = data.name
        textTtl.text = data.birthPlaceAndDate
        textGender.text = data.gender
        textAddress.text = data.fullAddress
        textReligion.text = data.religion
        textMarriageStatus.text = data.maritalStatus
        textJob.text = data.job
        textNationality.text = data.citizenship

        Glide.with(this@DetailProfileKTPActivity)
            .load(data.imageKTP)
            .into(imageIdCard)
    }
}