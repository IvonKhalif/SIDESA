package com.gov.sidesa.utils.modules

import com.gov.sidesa.data.letter.repository.LetterRepositoryImpl
import com.gov.sidesa.data.letter.repository.RegistrationRepositoryImpl
import com.gov.sidesa.data.user.UserRepositoryImpl
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.domain.registration.RegistrationRepository
import com.gov.sidesa.domain.user.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LetterRepository> { LetterRepositoryImpl(get()) }
    single<RegistrationRepository> { RegistrationRepositoryImpl(get()) }
}