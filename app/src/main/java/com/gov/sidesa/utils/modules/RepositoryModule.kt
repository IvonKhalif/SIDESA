package com.gov.sidesa.utils.modules

import com.gov.sidesa.data.letter.repository.LetterRepositoryImpl
import com.gov.sidesa.data.letter.repository.RegistrationRepositoryImpl
import com.gov.sidesa.data.letterdetail.repository.LetterDetailRepositoryImpl
import com.gov.sidesa.data.letterlist.repository.LetterListRepositoryImpl
import com.gov.sidesa.data.profile.repository.ProfileRepositoryImpl
import com.gov.sidesa.data.region.repository.RegionsRepositoryImpl
import com.gov.sidesa.data.user.UserRepositoryImpl
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository
import com.gov.sidesa.domain.letter.repository.LetterListRepository
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.domain.registration.RegistrationRepository
import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.user.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LetterRepository> { LetterRepositoryImpl(get()) }
    single<RegistrationRepository> { RegistrationRepositoryImpl(get()) }
    single<LetterDetailRepository> { LetterDetailRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<RegionsRepository> { RegionsRepositoryImpl(get()) }
    single<LetterListRepository> { LetterListRepositoryImpl(get()) }
}