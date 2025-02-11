package com.gov.sidesa.ui.profile.edit.kk

import android.app.Activity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.ActivityEditProfileKkactivityBinding
import com.gov.sidesa.ui.profile.edit.kk.models.AccountKKUiModel
import com.gov.sidesa.ui.profile.edit.kk.models.EditProfileKKUiEvent
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.utils.extension.downloadImage
import com.gov.sidesa.utils.extension.load
import com.gov.sidesa.utils.extension.setTextDistinct
import com.gov.sidesa.utils.picker.SelectImageBottomSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileKKActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileKkactivityBinding

    private val viewModel by viewModel<EditProfileKKViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileKkactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    private fun initView() = with(binding) {
        customToolbar.toolbarDetailProfile.setTitle(R.string.profile_edit_kk)

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initEvent() = with(binding) {
        buttonEditKk.setOnClickListener {
            viewModel.onEvent(EditProfileKKUiEvent.OnKKImageClicked)
        }

        buttonSave.setOnClickListener {
            viewModel.onEvent(EditProfileKKUiEvent.OnSubmit)
        }

        with(binding.customKkBiodata) {
            inputKkNumber.addTextChangedListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnKKChanged(it.toString()))
            }

            inputKkKepalaKeluarga.addTextChangedListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnFamilyHeadChanged(it.toString()))
            }
        }

        with(binding.containerAddress) {
            inputAddress.addTextChangedListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnAddressChanged(it.toString()))
            }

            inputRt.addTextChangedListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnRTChanged(it.toString()))
            }

            inputRw.addTextChangedListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnRWChanged(it.toString()))
            }

            inputProvince.setOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnProvinceClicked)
            }

            inputLayoutProvince.setEndIconOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnProvinceClicked)
            }

            inputCity.setOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnCityClicked)
            }

            inputLayoutCity.setEndIconOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnCityClicked)
            }

            inputKecamatan.setOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnDistrictClicked)
            }

            inputLayoutKecamatan.setEndIconOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnDistrictClicked)
            }

            inputKelurahan.setOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnVillageClicked)
            }

            inputLayoutKelurahan.setEndIconOnClickListener {
                viewModel.onEvent(EditProfileKKUiEvent.OnVillageClicked)
            }

            imageKk.setOnClickListener {
                viewModel.onRetryLoadImage()
            }
        }
    }

    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@EditProfileKKActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@EditProfileKKActivity) {
            showErrorMessage(it)
        }

        serverErrorState.observe(this@EditProfileKKActivity) {
            showErrorMessage(it)
        }

        closeScreenState.observe(this@EditProfileKKActivity) {
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uiModel.collectLatest {
                    updateUI(uiModel = it)
                }
            }
        }

        selectProvinceState.observe(this@EditProfileKKActivity) {
            val tag = "select_province"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createProvince()
                sheet.onSelected = {
                    viewModel.onProvinceSelected(region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectDistrictState.observe(this@EditProfileKKActivity) { cityId ->
            val tag = "select_district"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createDistrict(cityId = cityId)
                sheet.onSelected = {
                    viewModel.onDistrictSelected(region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectCityState.observe(this@EditProfileKKActivity) { provinceId ->
            val tag = "select_city"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createCity(
                    provinceId = provinceId
                )
                sheet.onSelected = {
                    viewModel.onCitySelected(region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectVillageState.observe(this@EditProfileKKActivity) { districtId ->
            val tag = "select_village"
            showImmediately(supportFragmentManager, tag) {
                val sheet = SelectRegionBottomSheet.createVillage(
                    districtId = districtId
                )
                sheet.onSelected = {
                    viewModel.onVillageSelected(region = it)
                    sheet.dismissAllowingStateLoss()
                }
                sheet
            }
        }

        selectPhotoState.observe(this@EditProfileKKActivity) {
            showImmediately(supportFragmentManager, "select_image") {
                val picker = SelectImageBottomSheet.newInstance()
                picker.onImageSelected = {
                    viewModel.onImageSelected(file = it)
                    picker.dismissAllowingStateLoss()
                }
                picker
            }
        }

        submitSuccessState.observe(this@EditProfileKKActivity) {
            showSuccessMessage(getString(it))
            setResult(Activity.RESULT_OK)
            finish()
        }

        imageLoaderState.observe(this@EditProfileKKActivity) {
            if (it == false) {
                binding.imageKk.setImageResource(R.drawable.ic_baseline_sync_24)
            }
        }
    }

    private var tempUrl = ""
    private fun updateUI(uiModel: AccountKKUiModel) = with(binding) {
        with(customKkBiodata) {
            inputKkNumber.setTextDistinct(uiModel.kk)
            inputKkKepalaKeluarga.setTextDistinct(uiModel.familyHeadName)
        }

        with(binding.containerAddress) {
            inputAddress.setTextDistinct(uiModel.address)
            inputRt.setTextDistinct(uiModel.rt)
            inputRw.setTextDistinct(uiModel.rw)

            inputProvince.setTextDistinct(uiModel.province.name)
            inputCity.setTextDistinct(uiModel.city.name)
            inputKecamatan.setTextDistinct(uiModel.district.name)
            inputKelurahan.setTextDistinct(uiModel.village.name)
        }

        if (uiModel.kkImageUri != tempUrl) {
            tempUrl = uiModel.kkImageUri

            imageKk.load(
                source = tempUrl,
                onSuccess = {
                    viewModel.onImageLoadState(isSuccess = true)
                },
                onFailed = {
                    viewModel.onImageLoadState(isSuccess = false)
                }
            )

            if (tempUrl.startsWith("http")) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val file = tempUrl.downloadImage(this@EditProfileKKActivity)
                        viewModel.onImageSelected(file)
                        viewModel.onImageLoadState(isSuccess = true)
                    } catch (_ :Throwable) {
                        viewModel.onImageLoadState(isSuccess = false)
                    }
                }
            }
        }
    }
}