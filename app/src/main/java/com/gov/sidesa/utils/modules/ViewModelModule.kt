package com.gov.sidesa.utils.modules

import com.gov.sidesa.ui.letter.input.LetterInputViewModel
import com.gov.sidesa.ui.letter.template.LetterTemplateViewModel
import com.gov.sidesa.ui.login.LoginViewModel
import com.gov.sidesa.ui.login.password.PasswordViewModel
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val ViewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordViewModel(get(), get()) }
    viewModel { LetterInputViewModel(get(), get(), get()) }
    viewModel { LetterTemplateViewModel(get()) }
    viewModel { RegistrationKTPViewModel(get()) }
}