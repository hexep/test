package com.ihexep.presentation.store.adapter

import androidx.core.view.isVisible
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.HomeStore
import com.ihexep.presentation.databinding.ItemHotSaleBinding
import com.ihexep.presentation.utils.CenterSquareTransformation

fun homeStoreItem(itemClickedListener: (HomeStore) -> Unit) =
    adapterDelegateViewBinding<HomeStore, Any, ItemHotSaleBinding>(
        { layoutInflater, root -> ItemHotSaleBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.tvPhoneModel.text = item.title
            binding.tvPhoneSubtitle.text = item.subtitle
            binding.ivNewPhone.isVisible = item.isNew

            binding.ivPhonePicture.load(item.picture) {
                crossfade(750)
                transformations(CenterSquareTransformation())
            }
        }
    }