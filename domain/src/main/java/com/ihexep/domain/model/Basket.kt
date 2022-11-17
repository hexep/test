package com.ihexep.domain.model

data class Basket(
    var basket: List<BasketItem>,
    var delivery: String,
    var id: String,
    var total: Int,
)