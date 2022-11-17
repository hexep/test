package com.ihexep.data.network.dto

import android.util.Log
import com.ihexep.domain.model.BasketItem
import kotlinx.serialization.Serializable

// Data transfer object model with mapper to business model

@Serializable
data class BasketItemDto (
    var id : Int? = null,
    var images: String? = null,
    var price: Int? = null,
    var title: String? = null
)

fun BasketItemDto.toBasketItem(): BasketItem? {
    if (id == null || price == null || price == 0) {
        Log.e("BasketItemDto","BasketItemDto id or price is not valid")
        return null
    }

    return BasketItem(
        id = id!!,
        images = images ?: "",
        price = price!!,
        title = title ?: "",
    )
}