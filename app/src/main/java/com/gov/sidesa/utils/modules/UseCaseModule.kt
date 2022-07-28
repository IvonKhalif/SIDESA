package com.gov.sidesa.utils.modules

import com.gov.sidesa.domain.letter.input.usecases.GetResourcesUseCase
import com.gov.sidesa.domain.user.CreatePasswordUseCase
import com.gov.sidesa.domain.user.LoginUseCase
import com.gov.sidesa.domain.user.ValidateNIKUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { ValidateNIKUseCase(get()) }
    single { LoginUseCase(get()) }
    single { CreatePasswordUseCase(get()) }
    single { GetResourcesUseCase(get()) }
}