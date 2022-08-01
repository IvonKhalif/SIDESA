package com.gov.sidesa.utils.modules

import com.gov.sidesa.ui.approval.DetailApprovalViewModel
import com.gov.sidesa.ui.approval.submissiondetail.DetailApprovalSubmissionViewModel
import com.gov.sidesa.ui.dashboard.DashboardViewModel
import com.gov.sidesa.ui.letter.detail.DetailSubmissionLetterViewModel
import com.gov.sidesa.ui.letter.input.LetterInputViewModel
import com.gov.sidesa.ui.letter.list.tab.TabLetterListViewModel
import com.gov.sidesa.ui.letter.template.LetterTemplateViewModel
import com.gov.sidesa.ui.login.LoginViewModel
import com.gov.sidesa.ui.login.password.PasswordViewModel
import com.gov.sidesa.ui.profile.detail.family.DetailProfileFamilyViewModel
import com.gov.sidesa.ui.profile.detail.kk.DetailProfileKKViewModel
import com.gov.sidesa.ui.profile.detail.ktp.DetailProfileKTPViewModel
import com.gov.sidesa.ui.profile.edit.family.EditProfileFamilyViewModel
import com.gov.sidesa.ui.profile.edit.kk.EditProfileKKViewModel
import com.gov.sidesa.ui.regions.SelectRegionViewModel
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val ViewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordViewModel(get(), get(), get()) }
    viewModel { LetterInputViewModel(get(), get(), get()) }
    viewModel { LetterTemplateViewModel(get()) }
    viewModel { RegistrationKTPViewModel(get()) }
    viewModel { DetailApprovalSubmissionViewModel() }
    viewModel { DetailProfileFamilyViewModel(get()) }
    viewModel { DetailProfileKKViewModel(get()) }
    viewModel { DetailProfileKTPViewModel(get()) }
    viewModel { SelectRegionViewModel(get(), get(), get(), get()) }
    viewModel { EditProfileFamilyViewModel(get(), get()) }
    viewModel { DetailApprovalViewModel(get(), get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { TabLetterListViewModel(get(), get()) }
    viewModel { DetailSubmissionLetterViewModel(get()) }
    viewModel { EditProfileKKViewModel() }
}