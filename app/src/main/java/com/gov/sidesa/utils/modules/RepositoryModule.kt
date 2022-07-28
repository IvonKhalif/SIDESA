package com.gov.sidesa.utils.modules

import com.gov.sidesa.data.letter.service.LetterInputRepositoryImpl
import com.gov.sidesa.data.user.UserRepositoryImpl
import com.gov.sidesa.domain.letter.input.repository.LetterInputRepository
import com.gov.sidesa.domain.user.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LetterInputRepository> { LetterInputRepositoryImpl(get()) }
}