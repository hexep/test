package com.ihexep.domain.model

data class Bestseller (
    var id: Int,
    var isFavorites: Boolean,
    var title: String,
    var priceWithoutDiscount: Int,
    var discountPrice: Int,
    var picture: String
)