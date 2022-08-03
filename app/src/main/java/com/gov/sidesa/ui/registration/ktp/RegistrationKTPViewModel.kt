package com.gov.sidesa.ui.registration.ktp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.registration.family.*
import com.gov.sidesa.data.registration.kk.KkBiodataModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.data.registration.ktp.BiodataKtpModel
import com.gov.sidesa.data.registration.ktp.GeneralKtpModel
import com.gov.sidesa.domain.profile.detail.family.models.asData
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.domain.registration.RegistrationUseCase
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.orZero
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class RegistrationKTPViewModel(
    private val registrationUseCase: RegistrationUseCase,
    private val getFamilyUseCase: GetFamilyUseCase
) : BaseViewModel() {

    var registrationStackState =
        MutableLiveData<RegistrationStackState>(RegistrationStackState.KtpBiodata)

    var biodataUiModel = MutableLiveData<BiodataUiModel>()

    var registrationStatus = MutableLiveData<String>()
    
    private val _closeScreenView = MutableLiveData<Unit>()
    val closeScreenView: LiveData<Unit> get() = _closeScreenView

    fun setPref(context: Context, key: String, value: String) {
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getPref(context: Context, key: String): String {
        val sharePref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return sharePref.getString(key, "").orEmpty()
    }

    fun registrationNewAccount(context: Context) = viewModelScope.launch {
        showLoadingWidget()
        val idAccount = PreferenceUtils.getUser()?.id

        val biodataPref = getPref(context, RegistrationStackState.KtpBiodata.toString())
        val biodataModel = Gson().fromJson(biodataPref, BiodataKtpModel::class.java)

        val kkPref = getPref(context, RegistrationStackState.KkBiodata.toString())
        val kkModel = Gson().fromJson(kkPref, KkBiodataModel::class.java)

        val generalPref = getPref(context, RegistrationStackState.KtpGeneral.toString())
        val generalModel = Gson().fromJson(generalPref, GeneralKtpModel::class.java)

        val addressPref = getPref(context, RegistrationStackState.KtpAddress.toString())
        val addressModel = Gson().fromJson(addressPref, AddressKtpModel::class.java)

        val ktpBase64 = getPref(context, RegistrationStackState.KtpUpload.toString())
        val kkBase64 = getPref(context, RegistrationStackState.KkUpload.toString())

        val fatherPref = getPref(context, RegistrationStackState.FamilyFather.toString())
        val fatherModel = Gson().fromJson(fatherPref, FamilyFatherModel::class.java)

        val motherPref = getPref(context, RegistrationStackState.FamilyMother.toString())
        val motherModel = Gson().fromJson(motherPref, FamilyMotherModel::class.java)

        val applicantPref = getPref(context, RegistrationStackState.FamilyApplicant.toString())
        val applicantModel = Gson().fromJson(applicantPref, FamilyApplicantModel::class.java)

        val childPref = getPref(context, RegistrationStackState.FamilyChild.toString())
        val childModel = Gson().fromJson(childPref, FamilyChildModel::class.java)

        val familyList = mutableListOf<FamilyListModel>()

        familyList.add(FamilyListModel.FatherModel(fatherModel))
        familyList.add(FamilyListModel.MotherModel(motherModel))
        familyList.add(FamilyListModel.ApplicantModel(applicantModel))
        familyList.add(FamilyListModel.ChildModel(childModel))

        val familyMap = mutableMapOf<String, String>()

        familyList.forEachIndexed { index, familyListModel ->
            when (familyListModel) {
                is FamilyListModel.FatherModel -> {
                    familyMap.putAll(
                        familyMapper(
                            index,
                            familyListModel.familyFatherModel.name,
                            familyListModel.familyFatherModel.nik,
                            familyListModel.familyFatherModel.placeOfBirth,
                            familyListModel.familyFatherModel.dateOfBirth,
                            "Ayah",
                            AddressKtpModel(
                                familyListModel.familyFatherModel.addressKtp?.address.orEmpty(),
                                familyListModel.familyFatherModel.addressKtp?.rt.orEmpty(),
                                familyListModel.familyFatherModel.addressKtp?.rw.orEmpty(),
                                familyListModel.familyFatherModel.addressKtp?.kecamatan.orEmpty(),
                                familyListModel.familyFatherModel.addressKtp?.kelurahan.orEmpty(),
                            )
                        )
                    )
                }
                is FamilyListModel.MotherModel -> {
                    familyMap.putAll(
                        familyMapper(
                            index,
                            familyListModel.familyMotherModel.name,
                            familyListModel.familyMotherModel.nik,
                            familyListModel.familyMotherModel.placeOfBirth,
                            familyListModel.familyMotherModel.dateOfBirth,
                            "Ibu",
                            AddressKtpModel(
                                familyListModel.familyMotherModel.addressKtp?.address.orEmpty(),
                                familyListModel.familyMotherModel.addressKtp?.rt.orEmpty(),
                                familyListModel.familyMotherModel.addressKtp?.rw.orEmpty(),
                                familyListModel.familyMotherModel.addressKtp?.kecamatan.orEmpty(),
                                familyListModel.familyMotherModel.addressKtp?.kelurahan.orEmpty(),
                            )
                        )
                    )
                }
                is FamilyListModel.ApplicantModel -> {
                    familyMap.putAll(
                        familyMapper(
                            index,
                            familyListModel.familyApplicantModel.name,
                            familyListModel.familyApplicantModel.nik,
                            familyListModel.familyApplicantModel.placeOfBirth,
                            familyListModel.familyApplicantModel.dateOfBirth,
                            familyListModel.familyApplicantModel.status,
                            AddressKtpModel(
                                familyListModel.familyApplicantModel.addressKtp?.address.orEmpty(),
                                familyListModel.familyApplicantModel.addressKtp?.rt.orEmpty(),
                                familyListModel.familyApplicantModel.addressKtp?.rw.orEmpty(),
                                familyListModel.familyApplicantModel.addressKtp?.kecamatan.orEmpty(),
                                familyListModel.familyApplicantModel.addressKtp?.kelurahan.orEmpty(),
                            )
                        )
                    )
                }
                is FamilyListModel.ChildModel -> {
                    familyMap.putAll(
                        familyMapper(
                            index,
                            familyListModel.familyChildModel.name,
                            familyListModel.familyChildModel.nik,
                            familyListModel.familyChildModel.placeOfBirth,
                            familyListModel.familyChildModel.dateOfBirth,
                            "Child",
                            AddressKtpModel(
                                familyListModel.familyChildModel.addressKtp?.address.orEmpty(),
                                familyListModel.familyChildModel.addressKtp?.rt.orEmpty(),
                                familyListModel.familyChildModel.addressKtp?.rw.orEmpty(),
                                familyListModel.familyChildModel.addressKtp?.kecamatan.orEmpty(),
                                familyListModel.familyChildModel.addressKtp?.kelurahan.orEmpty(),
                            )
                        )
                    )
                }
            }
        }

        when (val result = registrationUseCase.registrationNewAccount(
            idAccount = idAccount.orZero().toString(),
            nik = biodataModel.nik,
            noKk = kkModel.kkNumber,
            fullName = biodataModel.fullName,
            placeOfBirth = biodataModel.placeOdBirth,
            dateOfBirth = biodataModel.dateOfBirth,
            bloodType = biodataModel.bloodType,
            gender = biodataModel.gender,
            religion = generalModel.religion,
            marriageStatus = generalModel.marriageStatus,
            job = generalModel.job,
            address = addressModel.address,
            provinceId = "provinceId",
            cityId = "cityId",
            kecamatanId = "kecamatanId",
            kelurahanId = "kelurahanId",
            rt = addressModel.rt,
            rw = addressModel.rw,
            nationality = generalModel.nationality,
            ktpBase64 = ktpBase64,
            kkBase64 = kkBase64,
            familyMap = familyMap
        )) {
            is NetworkResponse.Success -> {
                registrationStatus.value = result.body
                updateDataUserLocal()
            }
            else -> {
                registrationStatus.value = ""
                hideLoadingWidget()
            }
        }
    }

    private fun updateDataUserLocal() = viewModelScope.launch {
        when (val result = getFamilyUseCase.invoke()) {
            is NetworkResponse.Success -> {
                PreferenceUtils.putUser(result.body.account.asData())
                _closeScreenView.value = Unit
            }
            else -> onResponseNotSuccess(response = result)
        }

        hideLoadingWidget()
    }

    private fun familyMapper(
        index: Int,
        name: String,
        nik: String,
        placeOfBirth: String,
        dateOfBirth: String,
        familyType: String,
        addressKtpModel: AddressKtpModel?,
    ): MutableMap<String, String> {
        val familyMap = mutableMapOf<String, String>()
        familyMap.apply {
            put("nama_lengkap_family[${index}]", name)
            put("no_ktp_family[${index}]", nik)
            put("tempat_lahir_family[${index}]", placeOfBirth)
            put("tanggal_lahir_family[${index}]", dateOfBirth)
            put("type_family[${index}]", familyType)

            put("alamat_family[${index}]", addressKtpModel?.address.orEmpty())
            put("rt_family[${index}]", addressKtpModel?.rt.orEmpty())
            put("rw_family[${index}]", addressKtpModel?.rw.orEmpty())
            put("id_kelurahan_family[${index}]", addressKtpModel?.kelurahan.orEmpty())
            put("id_kecamatan_family[${index}]", addressKtpModel?.kecamatan.orEmpty())
        }

        return familyMap
    }

}

data class BiodataUiModel(
    var nik: String = "",
    var fullName: String = "",
    var placeOfBirth: String = "",
    var dateOfBirth: String = "",
    var gender: String = "",
    var bloodType: String = ""
)