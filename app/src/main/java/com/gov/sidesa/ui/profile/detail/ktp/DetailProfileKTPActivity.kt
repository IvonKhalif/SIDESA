package com.gov.sidesa.ui.profile.detail.ktp

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailKtpprofileBinding
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.ui.profile.edit.ktp.EditProfileKTPActivity
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.ProfileConstant
import com.gov.sidesa.utils.constants.ProfileConstant.EXTRA_KTP_DETAIL
import com.gov.sidesa.utils.constants.UserExtrasConstant
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProfileKTPActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailKtpprofileBinding

    private val viewModel by viewModel<DetailProfileKTPViewModel>()
    private var detailProfileModel: AccountUiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKtpprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
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
        customToolbar.toolbarDetailProfile.apply {
            setTitle(R.string.profile_ktp)
            setNavigationOnClickListener {
                finish()
            }
        }
        buttonEditKtp.setOnClickListener {
            val intent = Intent(this@DetailProfileKTPActivity, EditProfileKTPActivity::class.java)
            intent.putExtra(EXTRA_KTP_DETAIL, detailProfileModel)
            resultLauncher.launch(intent)
        }

    }

    private fun assignDataToUi(data: AccountUiModel) = with(binding) {
        detailProfileModel = data
        textNik.text = data.nik
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

    override fun onResultData(result: Intent?) {
        super.onResultData(result)
        if (result?.getBooleanExtra(UserExtrasConstant.EXTRA_KTP_UPDATED, false) == true) {
            viewModel.onLoadKTP()
            showSuccessMessage(getString(R.string.general_success))
        }
    }
}