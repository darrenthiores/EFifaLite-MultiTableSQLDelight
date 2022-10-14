package com.darrenthiores.efifalite.di

import androidx.paging.ExperimentalPagingApi
import com.darrenthiores.core.domain.FootballInteractor
import com.darrenthiores.core.domain.FootballUseCase
import com.darrenthiores.efifalite.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagingApi
val useCaseModule = module {

    factory<FootballUseCase> { FootballInteractor(get()) }

}

val viewModelModule = module {

    viewModel { AppViewModel(get()) }
    viewModel { OverviewViewModel(get()) }
    viewModel { DrawViewModel(get()) }
    viewModel { LineUpViewModel(get()) }
    viewModel { ChangeViewModel(get()) }

}