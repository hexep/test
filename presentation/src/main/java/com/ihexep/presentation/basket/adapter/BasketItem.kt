package com.ihexep.presentation.basket.adapter

import coil.load
import coil.transform.RoundedCornersTransformation
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.BasketItem
import com.ihexep.presentation.databinding.ItemBasketBinding
import java.text.NumberFormat
import java.util.*

fun basketItem() = adapterDelegateViewBinding<BasketItem, Any, ItemBasketBinding>(
    { layoutInflater, root -> ItemBasketBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.ivImage.load(item.images) {
            transformations(RoundedCornersTransformation(20F))
        }
        binding.tvTitle.text = item.title
        binding.btnCount.text = "2"
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        binding.tvPrice.text = formatter.format(item.price)
    }
}