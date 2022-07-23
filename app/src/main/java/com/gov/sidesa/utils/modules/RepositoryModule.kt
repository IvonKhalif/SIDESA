package com.gov.sidesa.utils.modules

import com.gov.sidesa.data.user.UserRepositoryImpl
import com.gov.sidesa.domain.user.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}