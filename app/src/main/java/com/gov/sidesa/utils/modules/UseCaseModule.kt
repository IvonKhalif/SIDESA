package com.gov.sidesa.utils.modules

import com.gov.sidesa.domain.letter.detail.DoApprovalUseCase
import com.gov.sidesa.domain.letter.detail.GetLetterDetailUseCase
import com.gov.sidesa.domain.letter.input.usecases.GetLetterLayoutUseCase
import com.gov.sidesa.domain.letter.input.usecases.GetResourcesUseCase
import com.gov.sidesa.domain.letter.input.usecases.SaveLetterUseCase
import com.gov.sidesa.domain.letter.input.usecases.UpdateWidgetFromLocalUseCase
import com.gov.sidesa.domain.letter.list.usecase.ApprovalLettersUseCase
import com.gov.sidesa.domain.letter.list.usecase.SubmissionLetterUseCase
import com.gov.sidesa.domain.letter.template.usecases.GetTemplateUseCase
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.domain.profile.detail.family.usecases.UpdateDataKTPUseCase
import com.gov.sidesa.domain.profile.edit.family.usecases.UpdateProfileFamilyUseCase
import com.gov.sidesa.domain.profile.edit.kk.usecases.UpdateProfileKKUseCase
import com.gov.sidesa.domain.regions.usecases.city.GetCityUseCase
import com.gov.sidesa.domain.regions.usecases.district.GetDistrictUseCase
import com.gov.sidesa.domain.regions.usecases.province.GetProvinceUseCase
import com.gov.sidesa.domain.regions.usecases.village.GetVillageUseCase
import com.gov.sidesa.domain.registration.RegistrationUseCase
import com.gov.sidesa.domain.user.usecase.CreatePasswordUseCase
import com.gov.sidesa.domain.user.usecase.LoginUseCase
import com.gov.sidesa.domain.user.usecase.ResetPasswordUseCase
import com.gov.sidesa.domain.user.usecase.ValidateNIKUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { ValidateNIKUseCase(get()) }
    single { LoginUseCase(get()) }
    single { CreatePasswordUseCase(get()) }
    single { ResetPasswordUseCase(get()) }
    single { GetResourcesUseCase(get()) }
    single { GetLetterLayoutUseCase(get(), get()) }
    single { GetTemplateUseCase(get()) }
    single { SaveLetterUseCase(get()) }
    single { GetLetterDetailUseCase(get()) }
    single { SubmissionLetterUseCase(get()) }
    single { ApprovalLettersUseCase(get()) }
    single { DoApprovalUseCase(get()) }
    single { GetFamilyUseCase(get()) }
    single { GetProvinceUseCase(get()) }
    single { GetCityUseCase(get()) }
    single { GetDistrictUseCase(get()) }
    single { GetVillageUseCase(get()) }
    single { RegistrationUseCase(get()) }
    single { UpdateDataKTPUseCase(get()) }
    single { UpdateProfileFamilyUseCase(get()) }
    single { UpdateProfileKKUseCase(get()) }
    single { UpdateWidgetFromLocalUseCase() }
}