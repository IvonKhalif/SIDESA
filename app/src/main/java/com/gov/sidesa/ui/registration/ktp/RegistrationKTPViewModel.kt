package com.gov.sidesa.ui.registration.ktp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gov.sidesa.ui.registration.RegistrationStackState

class RegistrationKTPViewModel : ViewModel() {

    var registrationStackState =
        MutableLiveData<RegistrationStackState>(RegistrationStackState.KtpBiodata)

    var biodataUiModel = MutableLiveData<BiodataUiModel>()

}

data class BiodataUiModel(
    var nik: String = "",
    var fullName: String = "",
    var placeOfBirth: String = "",
    var dateOfBirth: String = "",
    var gender: String = "",
    var bloodType: String = ""
)