package com.gov.sidesa.utils.modules

import com.gov.sidesa.ui.login.LoginViewModel
import com.gov.sidesa.ui.login.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordViewModel(get(), get()) }
}