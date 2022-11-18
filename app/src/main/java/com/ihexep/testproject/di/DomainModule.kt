package com.ihexep.testproject.di

import com.ihexep.domain.usecases.*
import org.koin.dsl.module

val DomainModule = module {
    single { GetCategoriesUseCase() }
    single { GetBasketUseCase(get()) }
    single { GetPhonesUseCase(get()) }
    single { GetPhoneDetailsUseCase(get()) }
}