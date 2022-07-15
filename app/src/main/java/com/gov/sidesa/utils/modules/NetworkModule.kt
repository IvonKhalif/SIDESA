package com.gov.sidesa.utils.modules

import com.gov.sidesa.utils.NetworkUtil
import org.koin.dsl.module

val NetworkModule = module {
    single { NetworkUtil.buildClient(get()) }
}