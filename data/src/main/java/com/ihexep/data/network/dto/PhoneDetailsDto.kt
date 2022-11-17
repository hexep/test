package com.ihexep.data.network.dto

import android.util.Log
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.domain.model.ProductImage
import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName

// Data transfer object model with mapper to business model

@Serializable
data class PhoneDetailsDto(
    @SerializedName("CPU")
    var cpu: String? = null,
    var camera: String? = null,
    var capacity: List<String>? = null,
    var color: List<String>? = null,
    var id: String? = null,
    var images: List<String>? = null,
    var isFavorites: Boolean? = null,
    var price: Int? = null,
    var rating: Double? = null,
    var sd: String? = null,
    var ssd: String? = null,
    var title: String? = null
)

fun PhoneDetailsDto.toCellphoneDetails(): PhoneDetails? {
    if (id == null || price == null || price == 0) {
        Log.e("CellphoneDetailsDto", "CellphoneDetails id or price is not valid")
        return null
    }

    return PhoneDetails(
        cpu = cpu ?: "",
        camera = camera ?: "",
        capacity = capacity ?: listOf(),
        color = color ?: listOf(),
        images = images?.map { ProductImage(it) } ?: listOf(),
        isFavorites = isFavorites ?: false,
        rating = rating ?: 5.0,
        sd = sd ?: "",
        ssd = ssd ?: "",
        title = title ?: "",
        price = price!!,
        id = id!!
    )
}