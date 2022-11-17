package com.ihexep.data.network.dto

import android.util.Log
import com.ihexep.domain.model.HomeStore
import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName

// Data transfer object model with mapper to business model

@Serializable
data class HomeStoreDto(
    var id: Int? = null,
    @SerializedName("is_new")
    var isNew: Boolean? = null,
    var title: String? = null,
    var subtitle: String? = null,
    var picture: String? = null,
    @SerializedName("is_buy")
    var isBuy: Boolean? = null
)

fun HomeStoreDto.toHomeStore(): HomeStore? {
    if (id == null || title == null && picture == null) {
        Log.e("HomeStoreDto", "HomeStoreDto id, title or picture is not valid")
        return null
    }

    return HomeStore(
        id = id!!,
        isNew = isNew ?: false,
        title = title ?: "",
        subtitle = subtitle ?: "",
        picture = picture ?: ""
    )
}