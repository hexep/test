package com.ihexep.data.repository

import com.ihexep.domain.common.Resource
import com.ihexep.data.network.api.PhoneApi
import com.ihexep.data.network.dto.toCellphoneDetails
import com.ihexep.data.network.dto.toProductList
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.domain.model.Phones
import com.ihexep.domain.repository.PhoneRepository
import kotlinx.coroutines.flow.*

class PhoneRepositoryImpl(private val api: PhoneApi): PhoneRepository {

    override fun getAll(): Flow<Resource<Phones>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getAll()
            emit(Resource.Loaded(response.toProductList()!!))
        } catch (e: NullPointerException) {
            emit(Resource.Error("GetAll response is successful but null"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "GetAll: unexpected error"))
        }
    }

    override fun getDetails(): Flow<Resource<PhoneDetails>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getDetails()
            emit(Resource.Loaded(response.toCellphoneDetails()!!))
        } catch (e: NullPointerException) {
            emit(Resource.Error("GetDetails response is successful but null"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "GetDetails: unexpected error"))
        }
    }

}