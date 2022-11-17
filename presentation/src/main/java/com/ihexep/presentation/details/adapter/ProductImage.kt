package com.ihexep.presentation.details.adapter

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.ProductImage
import com.ihexep.presentation.databinding.ItemProductImageBinding

fun productImage(itemClickedListener : (ProductImage) -> Unit) = adapterDelegateViewBinding<ProductImage, Any, ItemProductImageBinding>(
    { layoutInflater, root -> ItemProductImageBinding.inflate(layoutInflater, root, false) }
) {
    binding.root.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        binding.ivProductPicture.load(item.url) { crossfade(750) }
    }
}