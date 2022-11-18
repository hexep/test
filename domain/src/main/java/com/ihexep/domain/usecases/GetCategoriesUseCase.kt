package com.ihexep.domain.usecases

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Categories
import com.ihexep.domain.model.ProductCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoriesUseCase {

    operator fun invoke(): Flow<Resource<List<ProductCategory>>> = flow {
        emit(Resource.Loading)
        val categories = Categories.values().map {
            ProductCategory(
                it.id,
                it.title,
                it.imageUrl
            )
        }
        emit(Resource.Loaded(categories))
    }
        .flowOn(Dispatchers.IO)

}