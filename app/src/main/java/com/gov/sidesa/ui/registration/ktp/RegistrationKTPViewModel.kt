package com.gov.sidesa.ui.registration.ktp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gov.sidesa.ui.registration.RegistrationStackState

class RegistrationKTPViewModel : ViewModel() {

    var registrationStackState =
        MutableLiveData<RegistrationStackState>(RegistrationStackState.KtpBiodata)

    var increaseProgress = MutableLiveData(false)
    var decreaseProgress = MutableLiveData(false)

}