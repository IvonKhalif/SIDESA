package com.example.containertracker.utils.modules

import com.example.containertracker.ui.container.ContainerViewModel
import com.example.containertracker.ui.container.detail.ContainerDetailViewModel
import com.example.containertracker.ui.history.HistoryViewModel
import com.example.containertracker.ui.history.detail.HistoryDetailViewModel
import com.example.containertracker.ui.home.HomeViewModel
import com.example.containertracker.ui.home.containercondition.ContainerConditionViewModel
import com.example.containertracker.ui.home.salesordernumber.SalesOrderNumberViewModel
import com.example.containertracker.ui.login.LoginViewModel
import com.example.containertracker.ui.main.MainViewModel
import com.example.containertracker.ui.report.ReportViewModel
import com.example.containertracker.ui.scanner.ScannerViewModel
import com.example.containertracker.ui.selectlocation.SelectLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SelectLocationViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { HistoryDetailViewModel(get()) }
    viewModel { ReportViewModel() }
    viewModel { ScannerViewModel(get()) }
    viewModel { ContainerConditionViewModel() }
    viewModel { SalesOrderNumberViewModel() }
    viewModel { ContainerViewModel(get()) }
    viewModel { ContainerDetailViewModel(get()) }
}