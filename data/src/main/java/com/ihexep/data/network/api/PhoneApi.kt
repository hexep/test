package com.ihexep.data.network.api

import com.ihexep.data.network.dto.PhonesDto
import com.ihexep.data.network.dto.PhoneDetailsDto
import retrofit2.http.GET

interface PhoneApi {
    @GET("/v3/654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getAll(): PhonesDto

    @GET("/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getDetails(): PhoneDetailsDto
}