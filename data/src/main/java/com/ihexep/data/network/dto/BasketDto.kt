package com.ihexep.data.network.dto

import android.util.Log
import com.ihexep.domain.model.Basket
import kotlinx.serialization.Serializable

// Data transfer object model with mapper to business model

@Serializable
data class BasketDto(
    var basket: List<BasketItemDto>? = null,
    var delivery: String? = null,
    var id: String? = null,
    var total: Int? = null
)

fun BasketDto.toBasket(): Basket? {
    if (id == null) {
        Log.e("BasketDto","BasketDto id is not valid")
        return null
    }

    return Basket(
        basket = basket?.mapNotNull { it.toBasketItem() } ?: listOf(),
        delivery = delivery ?: "",
        total = total ?: basket?.size ?: 0,
        id = id!!
    )
}