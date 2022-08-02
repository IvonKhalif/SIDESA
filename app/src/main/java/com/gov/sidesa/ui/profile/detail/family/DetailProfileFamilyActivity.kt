package com.gov.sidesa.ui.profile.detail.family

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailProfileFamilyBinding
import com.gov.sidesa.ui.profile.detail.family.adapter.DetailProfileFamilyAdapter
import com.gov.sidesa.ui.profile.edit.family.EditProfileFamilyActivity
import com.gov.sidesa.utils.dp
import com.gov.sidesa.utils.picker.RecyclerViewItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProfileFamilyActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProfileFamilyBinding

    private val viewModel by viewModel<DetailProfileFamilyViewModel>()

    private val adapter by lazy {
        DetailProfileFamilyAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileFamilyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    private fun initView() = with(binding) {
        customToolbar.toolbarDetailProfile.setTitle(R.string.family_data_detail)
        recyclerFamily.adapter = adapter
        recyclerFamily.layoutManager = LinearLayoutManager(
            this@DetailProfileFamilyActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerFamily.addItemDecoration(
            RecyclerViewItemDecoration(height = 2.dp, color = R.color.gray_D1D1D1)
        )
    }

    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@DetailProfileFamilyActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@DetailProfileFamilyActivity) {
            showErrorMessage(throwable = it)
            finish()
        }

        serverErrorState.observe(this@DetailProfileFamilyActivity) {
            showErrorMessage(message = it.status.orEmpty())
            finish()
        }

        familyData.observe(this@DetailProfileFamilyActivity) {
            binding.recyclerFamily.post {
                adapter.submitList(it)
            }
        }

        closeScreenState.observe(this@DetailProfileFamilyActivity) {
            finish()
        }
    }

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.onEditResult()
        }
    }

    private fun initEvent() = with(binding) {
        buttonEditFamily.setOnClickListener {
            val intent = Intent(
                this@DetailProfileFamilyActivity,
                EditProfileFamilyActivity::class.java
            )
            editLauncher.launch(intent)
        }

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }
}