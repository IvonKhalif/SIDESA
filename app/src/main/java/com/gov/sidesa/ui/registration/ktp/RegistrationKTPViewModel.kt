package com.gov.sidesa.ui.registration.ktp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.registration.family.*
import com.gov.sidesa.data.registration.kk.KkBiodataModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.data.registration.ktp.BiodataKtpModel
import com.gov.sidesa.data.registration.ktp.GeneralKtpModel
import com.gov.sidesa.domain.registration.RegistrationUseCase
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class RegistrationKTPViewModel(
    private val registrationUseCase: RegistrationUseCase
) : BaseViewModel() {

    var registrationStackState =
        MutableLiveData<RegistrationStackState>(RegistrationStackState.KtpBiodata)

    var biodataUiModel = MutableLiveData<BiodataUiModel>()

    var registrationStatus = MutableLiveData<String>()

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

        val familyMap = mutableMapOf<String, String>()
        val familyList = mutableListOf<FamilyListModel>()
        familyList.add(FamilyListModel(fatherModel, motherModel, applicantModel, childModel))
        familyList.forEachIndexed { index, familyListModel ->
            familyMap.apply {
                put(
                    "nama_lengkap_family", listOf(
                        familyListModel.fatherModel.name,
                        familyListModel.motherModel.name,
                        familyListModel.applicantModel.name,
                        familyListModel.childModel.name
                    ).toString()
                )
                put(
                    "no_ktp_family", listOf(
                        familyListModel.fatherModel.nik,
                        familyListModel.motherModel.nik,
                        familyListModel.applicantModel.nik,
                        familyListModel.childModel.nik,
                    ).toString()
                )
                put(
                    "tempat_lahir_family", listOf(
                        familyListModel.fatherModel.placeOfBirth,
                        familyListModel.motherModel.placeOfBirth,
                        familyListModel.applicantModel.placeOfBirth,
                        familyListModel.childModel.placeOfBirth,
                    ).toString()
                )
                put(
                    "type_family", listOf(
                        "Ayah",
                        "Ibu",
                        familyListModel.applicantModel.status,
                        "Anak 1"
                    ).toString()
                )

                if (familyListModel.fatherModel.isSameAddress) {
                    put(
                        "alamat_family", listOf(
                            familyListModel.fatherModel.addressKtp?.address.toString(),
                            familyListModel.motherModel.addressKtp?.address.toString(),
                            familyListModel.applicantModel.addressKtp?.address.toString(),
                            familyListModel.childModel.addressKtp?.address.toString(),
                        ).toString()
                    )
                    put(
                        "rt_family", addressModel.rt
                    )
                    put(
                        "rw_family", addressModel.rt
                    )
                    put(
                        "id_kelurahan_family", "kelurahanId"
                    )
                    put(
                        "id_kecamatan_family", "kecamatanId"
                    )
                } else {
                    put(
                        "alamat_family", listOf(
                            familyListModel.fatherModel.addressKtp?.address.toString(),
                            familyListModel.motherModel.addressKtp?.address.toString(),
                            familyListModel.applicantModel.addressKtp?.address.toString(),
                            familyListModel.childModel.addressKtp?.address.toString(),
                        ).toString()
                    )
                    put(
                        "rt_family", familyListModel.fatherModel.addressKtp?.rt.toString()
                    )
                    put(
                        "rw_family", familyListModel.fatherModel.addressKtp?.rw.toString()
                    )
                    put(
                        "id_kelurahan_family", "kelurahanId"
                    )
                    put(
                        "id_kecamatan_family", "kecamatanId"
                    )
                }

            }
        }

        when (val result = registrationUseCase.registrationNewAccount(
            biodataModel.nik,
            biodataModel.nik,
            kkModel.kkNumber,
            biodataModel.fullName,
            biodataModel.dateOfBirth,
            biodataModel.bloodType,
            generalModel.religion,
            generalModel.marriageStatus,
            generalModel.job,
            addressModel.address,
            "provinceId",
            "cityId",
            "kecamatanId",
            "kelurahanId",
            addressModel.rt,
            addressModel.rw,
            generalModel.nationality,
            ktpBase64,
            kkBase64,
            familyMap
        )) {
            is NetworkResponse.Success -> {
                registrationStatus.value = result.body
            }
            else -> {
                registrationStatus.value = ""
            }
        }
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