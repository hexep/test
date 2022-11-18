package com.ihexep.presentation.store.adapter

import androidx.recyclerview.widget.LinearSnapHelper
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.HomeStore
import com.ihexep.presentation.databinding.ItemHotSaleListBinding

fun homeStoreList(clickListener: (HomeStore) -> Unit) =
    adapterDelegateViewBinding<List<HomeStore>, Any, ItemHotSaleListBinding>(
        { layoutInflater, root -> ItemHotSaleListBinding.inflate(layoutInflater, root, false) },
        on = { item: Any, _, _ -> item is List<*> && item.isNotEmpty() && item[0] is HomeStore }
    ) {
        val helper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.rvHotSales)

        bind {
            val adapter = ListDelegationAdapter(homeStoreItem { clickListener(it) })
            adapter.items = item
            binding.rvHotSales.adapter = adapter
        }
    }