package com.gov.sidesa.ui.profile.edit.family

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.ActivityEditProfileFamilyBinding
import com.gov.sidesa.ui.profile.edit.family.adapter.EditProfileFamilyAdapter
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.utils.picker.RecyclerViewItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFamilyActivity : BaseActivity() {
    private lateinit var binding: ActivityEditProfileFamilyBinding

    private val viewModel: EditProfileFamilyViewModel by viewModel()

    private val adapter by lazy {
        EditProfileFamilyAdapter(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileFamilyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@EditProfileFamilyActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@EditProfileFamilyActivity) {
            showErrorMessage(it)
        }

        serverErrorState.observe(this@EditProfileFamilyActivity) {
            showErrorMessage(it)
        }

        closeScreenState.observe(this@EditProfileFamilyActivity) {
            finish()
        }

        componentData.observe(this@EditProfileFamilyActivity) {
            binding.rvComponents.post {
                adapter.submitList(it)
            }
        }

        selectProvinceState.observe(this@EditProfileFamilyActivity) { uiModel ->
            val tag = "select_province"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createProvince()
                sheet.onSelected = {
                    viewModel.onProvinceSelected(uiModel = uiModel, region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectDistrictState.observe(this@EditProfileFamilyActivity) { uiModel ->
            val tag = "select_district"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createDistrict(cityId = uiModel.city?.id ?: 0)
                sheet.onSelected = {
                    viewModel.onDistrictSelected(uiModel = uiModel, region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectCityState.observe(this@EditProfileFamilyActivity) { uiModel ->
            val tag = "select_city"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createCity(
                    provinceId = uiModel.province?.id ?: 0
                )
                sheet.onSelected = {
                    viewModel.onCitySelected(uiModel = uiModel, region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectVillageState.observe(this@EditProfileFamilyActivity) { uiModel ->
            val tag = "select_village"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createVillage(
                    districtId = uiModel.district?.id ?: 0
                )
                sheet.onSelected = {
                    viewModel.onVillageSelected(uiModel = uiModel, region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectBirthDateState.observe(this@EditProfileFamilyActivity) { uiModel ->
            showImmediately(supportFragmentManager, "select_birth_date") {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pilih Tanggal Lahir")
                    .build()
                picker.addOnPositiveButtonClickListener {
                    viewModel.onBirthDateSelected(uiModel = uiModel, millis = it)
                }
                picker
            }
        }
    }

    private fun initView() = with(binding) {
        customToolbar.toolbarDetailProfile.setTitle(R.string.family_data_edit)

        rvComponents.adapter = adapter
        rvComponents.layoutManager = LinearLayoutManager(
            this@EditProfileFamilyActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvComponents.addItemDecoration(RecyclerViewItemDecoration())
        rvComponents.itemAnimator = null
    }

    private fun initEvent() = with(binding) {
        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }
}