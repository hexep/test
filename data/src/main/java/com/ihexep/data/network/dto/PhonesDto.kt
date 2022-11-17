package com.ihexep.data.network.dto

import com.ihexep.domain.model.Phones
import com.google.gson.annotations.SerializedName

// Data transfer object model with mapper to business model

data class PhonesDto (
    @SerializedName("home_store"  )
    var homeStore  : List<HomeStoreDto>  = listOf(),
    @SerializedName("best_seller" )
    var bestSeller : List<BestsellerDto> = listOf()
)

fun PhonesDto.toProductList(): Phones? {
      val  homeStore  = homeStore.mapNotNull  { it.toHomeStore() }
      val  bestSeller = bestSeller.mapNotNull { it.toBestseller() }

    if (homeStore.isEmpty() && bestSeller.isEmpty()) return null

    return Phones(
        homeStore  = homeStore,
        bestSeller = bestSeller
    )
}