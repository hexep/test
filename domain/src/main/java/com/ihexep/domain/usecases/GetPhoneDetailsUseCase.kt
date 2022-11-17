package com.ihexep.domain.usecases

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.domain.repository.PhoneRepository
import kotlinx.coroutines.flow.*

class GetPhoneDetailsUseCase(private val repository: PhoneRepository) {

    operator fun invoke(): Flow<Resource<PhoneDetails>> = flow {
        repository.getDetails().collect { resource ->
            emit(resource)
        }
    }

}