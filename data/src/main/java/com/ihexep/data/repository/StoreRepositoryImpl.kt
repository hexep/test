package com.ihexep.data.repository

import com.ihexep.domain.common.Resource
import com.ihexep.data.network.api.StoreApi
import com.ihexep.data.network.dto.toBasket
import com.ihexep.domain.model.Basket
import com.ihexep.domain.repository.StoreRepository
import kotlinx.coroutines.flow.*

class StoreRepositoryImpl(private val api: StoreApi): StoreRepository {

    override fun getBasket(): Flow<Resource<Basket>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getBasket()
            emit(Resource.Loaded(response.toBasket()!!))
        } catch (e: NullPointerException) {
            emit(Resource.Error("GetBasket response is successful but null"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "GetBasket: unexpected error"))
        }
    }

}