package com.ihexep.presentation.store.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ihexep.domain.model.ProductCategory
import com.ihexep.presentation.databinding.ItemCategoryBinding

var lastSelectedPos = 0

fun categoryItem(clickListener: (ProductCategory) -> Unit) =
    adapterDelegateViewBinding<ProductCategory, Any, ItemCategoryBinding>(
    { layoutInflater, root -> ItemCategoryBinding.inflate(layoutInflater, root, false) }
) {

    binding.root.setOnClickListener {
        clickListener(item)

        val itemToDeselect = lastSelectedPos
        lastSelectedPos = item.id
        (it.parent as RecyclerView).adapter?.notifyItemChanged(itemToDeselect)
        it.isSelected = true
        binding.catIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        // Scroll clicked item to center
        val screenWidth = it.resources.displayMetrics.widthPixels
        val leftPadding = (it.parent as RecyclerView).paddingLeft
        val centerOfScreen = (screenWidth - it.width) / 2 - leftPadding
        val layoutManager = (it.parent as RecyclerView).layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(item.id, centerOfScreen)
    }

    bind {
        binding.root.isSelected = item.id == lastSelectedPos
        // Set color filter
        if (binding.root.isSelected) {
            binding.catIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        } else {
            binding.catIcon.clearColorFilter()
        }
        binding.catTitle.text = item.title

        val iconId = binding.root.context.resources.getIdentifier(
            item.imageUrl,
            "drawable",
            binding.root.context.packageName
        )

        binding.catIcon.setImageResource(iconId)
    }
}