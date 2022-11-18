package com.ihexep.presentation.store.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.Bestseller
import com.ihexep.presentation.databinding.ItemBestsellerListBinding

fun bestsellerList(clickListener: (Bestseller) -> Unit) =
    adapterDelegateViewBinding<List<Bestseller>, Any, ItemBestsellerListBinding>(
        { layoutInflater, root -> ItemBestsellerListBinding.inflate(layoutInflater, root, false) },
        on = { item: Any, _, _ -> item is List<*> && item.isNotEmpty() && item[0] is Bestseller }
    ) {
        bind {
            val adapter = ListDelegationAdapter(bestsellerItem { clickListener(it) })
            adapter.items = item
            binding.rvBestsellers.adapter = adapter
        }
    }