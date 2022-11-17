package com.ihexep.domain.repository

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Basket
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getBasket(): Flow<Resource<Basket>>
}