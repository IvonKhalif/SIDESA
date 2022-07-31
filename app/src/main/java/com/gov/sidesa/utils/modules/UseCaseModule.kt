package com.gov.sidesa.utils.modules

import com.gov.sidesa.domain.letter.input.usecases.GetLetterLayoutUseCase
import com.gov.sidesa.domain.letter.input.usecases.GetResourcesUseCase
import com.gov.sidesa.domain.letter.input.usecases.SaveLetterUseCase
import com.gov.sidesa.domain.letter.template.usecases.GetTemplateUseCase
import com.gov.sidesa.domain.registration.RegistrationUseCase
import com.gov.sidesa.domain.user.CreatePasswordUseCase
import com.gov.sidesa.domain.user.LoginUseCase
import com.gov.sidesa.domain.user.ValidateNIKUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { ValidateNIKUseCase(get()) }
    single { LoginUseCase(get()) }
    single { CreatePasswordUseCase(get()) }
    single { GetResourcesUseCase(get()) }
    single { GetLetterLayoutUseCase(get()) }
    single { GetTemplateUseCase(get()) }
    single { SaveLetterUseCase(get()) }
    single { RegistrationUseCase(get()) }
}