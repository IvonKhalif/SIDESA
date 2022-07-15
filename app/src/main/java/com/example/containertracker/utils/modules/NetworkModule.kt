package com.example.containertracker.utils.modules

import com.example.containertracker.data.container.ContainerService
import com.example.containertracker.data.history.HistoryService
import com.example.containertracker.data.location.LocationService
import com.example.containertracker.data.report.ReportService
import com.example.containertracker.data.salesorder.SalesOrderService
import com.example.containertracker.data.user.UserService
import com.example.containertracker.utils.NetworkUtil
import com.example.containertracker.utils.NetworkUtil.BASE_URL
import org.koin.dsl.module

val NetworkModule = module {
    single { NetworkUtil.buildClient(get()) }
    single { NetworkUtil.buildService<UserService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<LocationService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<ContainerService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<HistoryService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<ReportService>(BASE_URL, get()) }
    single { NetworkUtil.buildService<SalesOrderService>(BASE_URL, get()) }
}