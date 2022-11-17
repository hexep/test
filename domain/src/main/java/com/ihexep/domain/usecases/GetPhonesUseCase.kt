package com.ihexep.domain.usecases

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Phones
import com.ihexep.domain.repository.PhoneRepository
import kotlinx.coroutines.flow.*

class GetPhonesUseCase(private val repository: PhoneRepository) {

    operator fun invoke(): Flow<Resource<Phones>> = flow {
        repository.getAll().collect { resource -> emit(resource) }
    }

}