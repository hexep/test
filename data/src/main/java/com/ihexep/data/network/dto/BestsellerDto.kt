package com.ihexep.data.network.dto

import android.util.Log
import com.ihexep.domain.model.Bestseller
import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName

// Data transfer object model with mapper to business model

@Serializable
data class BestsellerDto(
    var id: Int? = null,
    @SerializedName("is_favorites")
    var isFavorites: Boolean? = null,
    var title: String? = null,
    @SerializedName("price_without_discount")
    var priceWithoutDiscount: Int? = null,
    @SerializedName("discount_price")
    var discountPrice: Int? = null,
    var picture: String? = null
)

fun BestsellerDto.toBestseller(): Bestseller? {
    if (id == null || discountPrice == null && priceWithoutDiscount == null) {
        Log.e("BestsellerDto", "BestsellerDto id or price is not valid")
        return null
    }

    return Bestseller(
        id = id!!,
        isFavorites = isFavorites ?: false,
        title = title ?: "",
        priceWithoutDiscount = priceWithoutDiscount ?: discountPrice!!,
        discountPrice = discountPrice ?: priceWithoutDiscount!!,
        picture = picture ?: ""
    )
}