package com.ihexep.presentation.store.adapter

import android.graphics.Paint
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.Bestseller
import com.ihexep.presentation.databinding.ItemBestsellerBinding
import java.text.NumberFormat
import java.util.*

fun bestsellerItem(itemClickedListener: (Bestseller) -> Unit) =
    adapterDelegateViewBinding<Bestseller, Any, ItemBestsellerBinding>(
        { layoutInflater, root -> ItemBestsellerBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.btnFavourite.isSelected = item.isFavorites
            binding.btnFavourite.setOnClickListener {
                it.isSelected = !it.isSelected
            }
            binding.ivPicture.load(item.picture) {
                crossfade(750)
            }
            binding.tvCellphoneTitle.text = item.title
            val formatter = NumberFormat.getCurrencyInstance(Locale.US)
            formatter.maximumFractionDigits = 0
            binding.tvPriceWithDiscount.text = formatter.format(item.discountPrice)
            binding.tvPriceWithoutDiscount.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                text = formatter.format(item.priceWithoutDiscount)
            }
        }
    }