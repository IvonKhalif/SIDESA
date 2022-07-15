package com.example.containertracker.utils.modules

import com.example.containertracker.data.container.ContainerRepositoryImpl
import com.example.containertracker.data.history.HistoryRepositoryImpl
import com.example.containertracker.data.location.LocationRepositoryImpl
import com.example.containertracker.data.report.ReportRepositoryImpl
import com.example.containertracker.data.salesorder.SalesOrderNumberRepositoryImpl
import com.example.containertracker.data.user.UserRepositoryImpl
import com.example.containertracker.domain.container.ContainerRepository
import com.example.containertracker.domain.history.HistoryRepository
import com.example.containertracker.domain.location.LocationRepository
import com.example.containertracker.domain.report.ReportRepository
import com.example.containertracker.domain.salesorder.SalesOrderNumberRepository
import com.example.containertracker.domain.user.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LocationRepository> { LocationRepositoryImpl(get()) }
    single<ContainerRepository> { ContainerRepositoryImpl(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
    single<ReportRepository> { ReportRepositoryImpl(get()) }
    single<SalesOrderNumberRepository> { SalesOrderNumberRepositoryImpl(get()) }
}