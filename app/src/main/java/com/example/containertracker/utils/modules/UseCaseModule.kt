package com.example.containertracker.utils.modules

import com.example.containertracker.domain.container.ContainerListUseCase
import com.example.containertracker.domain.container.ContainerQRUseCase
import com.example.containertracker.domain.container.ContainerUseCase
import com.example.containertracker.domain.container.SaveHistoryUseCase
import com.example.containertracker.domain.history.GetHistoryUseCase
import com.example.containertracker.domain.location.LocationsUseCase
import com.example.containertracker.domain.report.GetReportDataUseCase
import com.example.containertracker.domain.salesorder.GetSalesOrderNumberUseCase
import com.example.containertracker.domain.user.SignInUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { SignInUseCase(get()) }
    single { LocationsUseCase(get()) }
    single { ContainerUseCase(get()) }
    single { SaveHistoryUseCase(get()) }
    single { GetHistoryUseCase(get()) }
    single { GetReportDataUseCase(get()) }
    single { GetSalesOrderNumberUseCase(get()) }
    single { ContainerListUseCase(get()) }
    single { ContainerQRUseCase(get()) }
}