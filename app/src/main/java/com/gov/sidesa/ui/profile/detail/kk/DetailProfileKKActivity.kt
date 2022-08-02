package com.gov.sidesa.ui.profile.detail.kk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailProfileKkactivityBinding
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.ui.profile.edit.kk.EditProfileKKActivity
import com.gov.sidesa.utils.NetworkUtil
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

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.onEditResult()
        }
    }

    private fun initEvent() = with(binding) {
        buttonUpdateKk.setOnClickListener {
            val intent = Intent(this@DetailProfileKKActivity, EditProfileKKActivity::class.java)
            editLauncher.launch(intent)
        }

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun assignDataToUi(data: AccountUiModel) = with(binding) {
        textKk.text = data.kk
        textKepalaKeluarga.text = data.familyHead
        textAddress.text = data.fullAddressKK

        Glide.with(this@DetailProfileKKActivity)
            .load(NetworkUtil.SERVER_HOST + data.imageKK)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageIdCard)
    }
}