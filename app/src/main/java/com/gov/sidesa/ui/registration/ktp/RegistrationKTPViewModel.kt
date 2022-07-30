package com.gov.sidesa.ui.registration.ktp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gov.sidesa.ui.registration.RegistrationStackState

class RegistrationKTPViewModel : ViewModel() {

    var registrationStackState =
        MutableLiveData<RegistrationStackState>(RegistrationStackState.KtpBiodata)

    var biodataUiModel = MutableLiveData<BiodataUiModel>()


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

}

data class BiodataUiModel(
    var nik: String = "",
    var fullName: String = "",
    var placeOfBirth: String = "",
    var dateOfBirth: String = "",
    var gender: String = "",
    var bloodType: String = ""
)