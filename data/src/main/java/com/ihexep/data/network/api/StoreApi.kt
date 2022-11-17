package com.ihexep.data.network.api

import com.ihexep.data.network.dto.BasketDto
import retrofit2.http.GET

interface StoreApi {
    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getBasket(): BasketDto
}