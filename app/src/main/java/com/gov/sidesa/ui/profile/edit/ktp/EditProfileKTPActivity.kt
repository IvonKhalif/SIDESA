package com.gov.sidesa.ui.profile.edit.ktp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.data.profile.request.EditProfileKTPRequest
import com.gov.sidesa.databinding.ActivityEditKtpprofileBinding
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.utils.constants.ProfileConstant
import com.gov.sidesa.utils.constants.UserExtrasConstant
import com.gov.sidesa.utils.enums.StatusResponseEnum
import com.gov.sidesa.utils.extension.format
import com.gov.sidesa.utils.extension.isNullOrZero
import com.gov.sidesa.utils.picker.SelectImageBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileKTPActivity : BaseActivity() {

    private lateinit var binding: ActivityEditKtpprofileBinding
    private val viewModel by viewModel<EditProfileKTPViewModel>()

    private val bindingBiodataKtp by lazy { binding.customKtpBiodata }
    private val bindingAddress by lazy { binding.customAddress }
    private val bindingGeneralKtp by lazy { binding.customKtpGeneral }
    private val ktpDetail by lazy {
        intent.getParcelableExtra<AccountUiModel>(ProfileConstant.EXTRA_KTP_DETAIL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditKtpprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
        initObserver()
        setDatePicker()
        setDropDownGender()
        setDropDownBloodType()
    }

    private fun initView() = with(binding) {
        ktpDetail?.let { viewModel.setDataProfile(it) }
        customToolbar.toolbarDetailProfile.apply {
            setTitle(R.string.profile_edit_ktp_title)
            setNavigationOnClickListener {
                finish()
            }
        }
        Glide.with(this@EditProfileKTPActivity)
            .load(ktpDetail?.imageKTP)
            .into(binding.imageKtp)

        setDropDownReligion()
        setDropDownMarriageStatus()
        setDropDownJob()
        setDropDownNationality()
    }

    private fun initEvent() {
        bindingAddress.inputKtpKelurahan.setOnClickListener {
            if (viewModel.inputKtpKecamatan.value == null || viewModel.inputKtpKecamatan.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.district_has_not_been_selected))
            else
                showVillageBottomSheet()
        }

        bindingAddress.inputKtpKecamatan.setOnClickListener {
            if (viewModel.inputKtpCity.value == null || viewModel.inputKtpCity.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.city_has_not_been_selected))
            else
                showDistrictBottomSheet()
        }

        bindingAddress.inputKtpCity.setOnClickListener {
            if (viewModel.inputKtpProvince.value == null || viewModel.inputKtpProvince.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.province_has_not_been_selected))
            else
                showCityBottomSheet()
        }

        bindingAddress.inputKtpProvince.setOnClickListener {
            showProvinceBottomSheet()
        }

        binding.buttonSave.setOnClickListener {
            viewModel.updateKTP(createRequest())
        }

        binding.buttonEditKtp.setOnClickListener {
            showMediaDialog()
        }

        binding.imageKtp.setOnClickListener {
            showMediaDialog()
        }
    }

    private fun createRequest() = EditProfileKTPRequest(
        accountId = ktpDetail?.id.toString(),
        nik = bindingBiodataKtp.inputKtpNik.text.toString(),
        accountName = bindingBiodataKtp.inputKtpName.text.toString(),
        birthPlace = bindingBiodataKtp.inputKtpPlace.text.toString(),
        birthDate = viewModel.inputKtpDob.value,
        gender = bindingBiodataKtp.inputKtpGender.text.toString(),
        blood = bindingBiodataKtp.inputKtpBloodType.text.toString(),
        address = bindingAddress.inputKtpAddress.text.toString(),
        villageId = viewModel.inputKtpKelurahan.value?.id,
        districtId = viewModel.inputKtpKecamatan.value?.id,
        cityId = viewModel.inputKtpCity.value?.id,
        provinceId = viewModel.inputKtpProvince.value?.id,
        rt = bindingAddress.inputKtpRt.text.toString(),
        rw = bindingAddress.inputKtpRw.text.toString(),
        religion = bindingGeneralKtp.inputKtpReligion.text.toString(),
        job = bindingGeneralKtp.inputKtpJob.text.toString(),
        marriage = bindingGeneralKtp.inputKtpMarriage.text.toString(),
        nationality = bindingGeneralKtp.inputKtpNationality.text.toString(),
        imageKTP = viewModel.imageKTPBase64.value,
    )

    private fun showVillageBottomSheet() {
        val tag = "select_village"
        showImmediately(supportFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createVillage(
                districtId = viewModel.inputKtpKecamatan.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpKelurahan.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showDistrictBottomSheet() {
        val tag = "select_district"
        showImmediately(supportFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createDistrict(
                cityId = viewModel.inputKtpCity.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpKecamatan.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showCityBottomSheet() {
        val tag = "select_city"
        showImmediately(supportFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createCity(
                provinceId = viewModel.inputKtpProvince.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpCity.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showProvinceBottomSheet() {
        val tag = "select_province"
        showImmediately(supportFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createProvince()
            sheet.onSelected = {
                viewModel.inputKtpProvince.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun initObserver() {
        viewModel.apply {
            inputKtpNik.observe(this@EditProfileKTPActivity, ::updateUINik)
            inputKtpName.observe(this@EditProfileKTPActivity, ::updateUIName)
            inputKtpPlace.observe(this@EditProfileKTPActivity, ::updateUIBirthPlace)
            inputKtpDob.observe(this@EditProfileKTPActivity, ::updateUIBirthDate)
            inputKtpAddress.observe(this@EditProfileKTPActivity, ::updateUIAddress)
            inputKtpRt.observe(this@EditProfileKTPActivity, ::updateUIRt)
            inputKtpRw.observe(this@EditProfileKTPActivity, ::updateUIRw)
            inputKtpProvince.observe(this@EditProfileKTPActivity, ::updateUIProvince)
            inputKtpCity.observe(this@EditProfileKTPActivity, ::updateUICity)
            inputKtpKecamatan.observe(this@EditProfileKTPActivity, ::updateUIDistrict)
            inputKtpKelurahan.observe(this@EditProfileKTPActivity, ::updateUIVillage)
            inputKtpReligion.observe(this@EditProfileKTPActivity, ::updateUIReligion)
            inputKtpMarriage.observe(this@EditProfileKTPActivity, ::updateUIMarriage)
            inputKtpJob.observe(this@EditProfileKTPActivity, ::updateUIJob)
            inputKtpNationality.observe(this@EditProfileKTPActivity, ::updateUICitizenship)
            statusUpdateData.observe(this@EditProfileKTPActivity, ::handleStatusUpdate)
            loadingState.observe(this@EditProfileKTPActivity) {
                handleLoadingWidget(isLoading = it)
            }
        }
    }

    private fun handleStatusUpdate(status: String?) {
        if (status == StatusResponseEnum.SUCCESS.status) {
            val intent = Intent()
            intent.putExtra(UserExtrasConstant.EXTRA_KTP_UPDATED, true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateUIName(text: String) = bindingBiodataKtp.inputKtpName.setText(text)
    private fun updateUINik(text: String) = bindingBiodataKtp.inputKtpNik.setText(text)
    private fun updateUIBirthPlace(text: String) = bindingBiodataKtp.inputKtpPlace.setText(text)
    private fun updateUIBirthDate(date: Date) = bindingBiodataKtp.inputKtpDob.setText(date.format())
    private fun updateUIAddress(text: String) = bindingAddress.inputKtpAddress.setText(text)
    private fun updateUIRt(text: String) = bindingAddress.inputKtpRt.setText(text)
    private fun updateUIRw(text: String) = bindingAddress.inputKtpRw.setText(text)
    private fun updateUIProvince(region: Region) =
        bindingAddress.inputKtpProvince.setText(region.name)

    private fun updateUICity(region: Region) = bindingAddress.inputKtpCity.setText(region.name)
    private fun updateUIDistrict(region: Region) =
        bindingAddress.inputKtpKecamatan.setText(region.name)

    private fun updateUIVillage(region: Region) =
        bindingAddress.inputKtpKelurahan.setText(region.name)

    private fun updateUIReligion(text: String) = bindingGeneralKtp.inputKtpReligion.setText(text)
    private fun updateUIMarriage(text: String) = bindingGeneralKtp.inputKtpMarriage.setText(text)
    private fun updateUIJob(text: String) = bindingGeneralKtp.inputKtpJob.setText(text)
    private fun updateUICitizenship(text: String) =
        bindingGeneralKtp.inputKtpNationality.setText(text)

    private fun setDatePicker() {
        bindingBiodataKtp.inputKtpDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                bindingBiodataKtp.inputKtpDob.setText(format.format(date))
            }
            picker.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    private fun setDropDownBloodType() {
        val bloodTypes = resources.getStringArray(R.array.blood_type)
        val bloodTypeAdapter = ArrayAdapter(this, R.layout.item_dropdown, bloodTypes)
        val inputBloodTypeAutoComplete =
            findViewById<AutoCompleteTextView>(R.id.input_ktp_blood_type)
        inputBloodTypeAutoComplete.setAdapter(bloodTypeAdapter)
    }

    private fun setDropDownGender() {
        val genders = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(this, R.layout.item_dropdown, genders)
        val inputGenderAutoComplete = findViewById<AutoCompleteTextView>(R.id.input_ktp_gender)
        inputGenderAutoComplete.setAdapter(genderAdapter)
    }

    private fun setDropDownReligion() {
        val religionList = resources.getStringArray(R.array.religion)
        val religionAdapter = ArrayAdapter(this, R.layout.item_dropdown, religionList)
        val religionAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_religion)
        religionAutoComplete.setAdapter(religionAdapter)
    }

    private fun setDropDownMarriageStatus() {
        val marriageStatusList = resources.getStringArray(R.array.marriage_status)
        val marriageStatusAdapter =
            ArrayAdapter(this, R.layout.item_dropdown, marriageStatusList)
        val marriageStatusAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_marriage)
        marriageStatusAutoComplete.setAdapter(marriageStatusAdapter)
    }

    private fun setDropDownJob() {
        val jobList = resources.getStringArray(R.array.job)
        val jobAdapter = ArrayAdapter(this, R.layout.item_dropdown, jobList)
        val jobAutoComplete = binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_job)
        jobAutoComplete.setAdapter(jobAdapter)
    }

    private fun setDropDownNationality() {
        val nationalityList = resources.getStringArray(R.array.nationality)
        val nationalityAdapter =
            ArrayAdapter(this, R.layout.item_dropdown, nationalityList)
        val nationalityAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_nationality)
        nationalityAutoComplete.setAdapter(nationalityAdapter)
    }

    private fun showMediaDialog() {
        val media = SelectImageBottomSheet.newInstance()

        media.onImageSelected = {
            setImageKtp(it)
            media.dismissAllowingStateLoss()
        }

        media.showNow(supportFragmentManager, media.javaClass.canonicalName)
    }

    private fun setImageKtp(it: File) {
        viewModel.base46Image(it.path)
        Glide.with(this)
            .load(it)
            .into(binding.imageKtp)
    }
}