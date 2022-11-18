package com.ihexep.testproject.di

import com.ihexep.data.common.Constants.BASE_URL
import com.ihexep.data.network.api.BasketApi
import com.ihexep.data.network.api.PhoneApi
import com.ihexep.data.network.createWebService
import com.ihexep.data.repository.BasketRepositoryImpl
import com.ihexep.data.repository.PhoneRepositoryImpl
import com.ihexep.domain.repository.BasketRepository
import com.ihexep.domain.repository.PhoneRepository
import org.koin.dsl.module

val NetworkModule = module {
    single { createWebService<PhoneApi>(url = BASE_URL) }
    single { createWebService<BasketApi>(url = BASE_URL) }
    single { provideBasketRepository(api = get()) }
    single { providePhoneRepository(api = get()) }
}

fun provideBasketRepository(api: BasketApi): BasketRepository {
    return BasketRepositoryImpl(api)
}

fun providePhoneRepository(api: PhoneApi): PhoneRepository {
    return PhoneRepositoryImpl(api)
}
