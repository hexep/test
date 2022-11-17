package com.ihexep.domain.repository

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Phones
import com.ihexep.domain.model.PhoneDetails
import kotlinx.coroutines.flow.Flow

interface PhoneRepository {
    fun getAll(): Flow<Resource<Phones>>

    fun getDetails(): Flow<Resource<PhoneDetails>>
}