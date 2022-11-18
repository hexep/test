package com.ihexep.data.repository

import com.ihexep.domain.common.Resource
import com.ihexep.data.network.api.BasketApi
import com.ihexep.data.network.dto.toBasket
import com.ihexep.domain.model.Basket
import com.ihexep.domain.repository.BasketRepository
import kotlinx.coroutines.flow.*

class BasketRepositoryImpl(private val api: BasketApi): BasketRepository {

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