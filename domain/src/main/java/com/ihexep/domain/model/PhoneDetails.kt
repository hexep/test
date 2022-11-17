package com.ihexep.domain.model

data class PhoneDetails(
    var cpu: String,
    var camera: String,
    var capacity: List<String>,
    var color: List<String>,
    var id: String,
    var images: List<ProductImage>,
    var isFavorites: Boolean,
    var price: Int,
    var rating: Double,
    var sd: String,
    var ssd: String,
    var title: String
)

data class ProductImage (val url: String)
