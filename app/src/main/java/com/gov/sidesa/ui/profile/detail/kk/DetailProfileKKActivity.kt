package com.gov.sidesa.ui.profile.detail.kk

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailProfileKkactivityBinding
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.ui.profile.edit.kk.EditProfileKKActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProfileKKActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailProfileKkactivityBinding

    private val viewModel by viewModel<DetailProfileKKViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileKkactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@DetailProfileKKActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@DetailProfileKKActivity) {
            showErrorMessage(throwable = it)
            finish()
        }

        serverErrorState.observe(this@DetailProfileKKActivity) {
            showErrorMessage(message = it.status.orEmpty())
            finish()
        }

        profileKKData.observe(this@DetailProfileKKActivity) {
            assignDataToUi(data = it)
        }
    }

    private fun initView() = with(binding) {
        customToolbar.toolbarDetailProfile.setTitle(R.string.profile_kk)
    }

    private fun initEvent() = with(binding) {
        buttonUpdateKk.setOnClickListener {
            val intent = Intent(this@DetailProfileKKActivity, EditProfileKKActivity::class.java)
            resultLauncher.launch(intent)
        }

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun assignDataToUi(data: AccountUiModel) = with(binding) {
        textKk.text = data.kk
        textKepalaKeluarga.text = "Ivon Khalif"
        textAddress.text = "Tangerang"

        Glide.with(this@DetailProfileKKActivity)
            .load(data.imageKTP)
            .into(imageIdCard)
    }
}