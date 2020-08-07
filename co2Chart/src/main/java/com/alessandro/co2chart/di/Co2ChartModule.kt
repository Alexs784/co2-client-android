package com.alessandro.co2chart.di

import com.alessandro.co2chart.mapper.Co2EntityMapper
import com.alessandro.co2chart.ui.Co2ChartViewModel
import com.alessandro.co2chart.usecase.GetLatestCo2DataUseCase
import com.alessandro.co2chart.util.DateParser
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val co2ChartModule = module {
    factory { DateParser() }
    factory { Co2EntityMapper(get()) }
    factory { GetLatestCo2DataUseCase(get(), get()) }

    viewModel { Co2ChartViewModel(get()) }
}