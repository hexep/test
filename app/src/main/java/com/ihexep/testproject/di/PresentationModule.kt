package com.ihexep.testproject.di

import com.ihexep.presentation.basket.BasketViewModel
import com.ihexep.presentation.store.StoreViewModel
import com.ihexep.presentation.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel { StoreViewModel(categories = get(), phones = get()) }
    viewModel { DetailsViewModel(details = get()) }
    viewModel { BasketViewModel(basket = get()) }
}