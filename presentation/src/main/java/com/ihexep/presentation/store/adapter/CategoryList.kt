package com.ihexep.presentation.store.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.ProductCategory
import com.ihexep.presentation.databinding.ItemCategoryListBinding

fun categoryList() =
    adapterDelegateViewBinding<List<ProductCategory>, Any, ItemCategoryListBinding>(
        { layoutInflater, root ->
            ItemCategoryListBinding.inflate(
                layoutInflater,
                root,
                false
            )
        },
        on = { item: Any, _, _ ->
            item is List<*> && item.isNotEmpty() && item[0] is ProductCategory
        }
    ) {
        bind {
            var lastSelectedPos = 0

            val adapter = ListDelegationAdapter(
                categoryItem(
                    { product ->
                        binding.rvProductCategoryList.adapter?.notifyItemChanged(lastSelectedPos)
                        binding.rvProductCategoryList.adapter?.notifyItemChanged(product.id)
                        lastSelectedPos = product.id
                    },
                    { view, product ->
                        view.isSelected = product.id == lastSelectedPos
                    }),
            )
            adapter.items = item
            binding.rvProductCategoryList.adapter = adapter
        }
    }