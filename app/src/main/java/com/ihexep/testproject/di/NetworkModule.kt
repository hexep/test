package com.ihexep.testproject.di

import com.ihexep.data.common.Constants.BASE_URL
import com.ihexep.data.network.api.StoreApi
import com.ihexep.data.network.api.PhoneApi
import com.ihexep.data.network.createWebService
import com.ihexep.data.repository.StoreRepositoryImpl
import com.ihexep.data.repository.PhoneRepositoryImpl
import com.ihexep.domain.repository.StoreRepository
import com.ihexep.domain.repository.PhoneRepository
import org.koin.dsl.module

val NetworkModule = module {
    single { createWebService<PhoneApi>(url = BASE_URL) }
    single { createWebService<StoreApi>(url = BASE_URL) }
    single { provideStoreRepository(api = get()) }
    single { providePhoneRepository(api = get()) }
}

fun provideStoreRepository(api: StoreApi): StoreRepository {
    return StoreRepositoryImpl(api)
}

fun providePhoneRepository(api: PhoneApi): PhoneRepository {
    return PhoneRepositoryImpl(api)
}
