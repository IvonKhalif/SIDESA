package com.gov.sidesa.utils.modules

import com.gov.sidesa.data.letter.service.LetterService
import com.gov.sidesa.data.letterdetail.service.LetterDetailService
import com.gov.sidesa.data.user.UserService
import com.gov.sidesa.utils.NetworkUtil
import com.gov.sidesa.utils.NetworkUtil.BASE_URL
import org.koin.dsl.module

val NetworkModule = module {
    single { NetworkUtil.buildClient(get()) }
    single { NetworkUtil.buildService<UserService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<LetterService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<LetterDetailService>(BASE_URL, get()) }
}