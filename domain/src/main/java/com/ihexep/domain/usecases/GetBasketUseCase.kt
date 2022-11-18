package com.ihexep.domain.usecases

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Basket
import com.ihexep.domain.repository.BasketRepository
import kotlinx.coroutines.flow.*

class GetBasketUseCase(private val repository: BasketRepository) {

    operator fun invoke(): Flow<Resource<Basket>> = flow {
        repository.getBasket().collect { resource ->
            emit(resource)
        }
    }

}