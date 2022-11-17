package com.ihexep.presentation.details

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.presentation.R
import com.ihexep.presentation.databinding.FragmentDetailsBinding
import com.ihexep.presentation.details.adapter.productImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        // Makes the imageCarousel looks like in the design file
        setImageCarouselTransformations()

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_Details_to_MainScreen)
        }

        lifecycleScope.launch {
            viewModel.details.collect { state ->
                when (state) {
                    is Resource.Loaded -> { setScreenValues(state.data) }
                    is Resource.Loading -> { }
                    is Resource.Error -> { println(state.errorMessage) }
                }
            }
        }

        return binding.root
    }

    private fun setScreenValues(details: PhoneDetails) {
        binding.tvProductTitle.text = details.title

        binding.ratingBar.rating = details.rating.toFloat()

        binding.btnAddToFav.isSelected = details.isFavorites
        binding.btnAddToFav.setOnClickListener { it.isSelected = !it.isSelected }

        binding.tvCpu.text = details.cpu
        binding.tvCamera.text = details.camera
        binding.tvSd.text = details.sd
        binding.tvSsd.text = details.ssd

        binding.groupColors.removeAllViews()
        if (details.color.isNotEmpty()) { details.color.forEach { addColor(Color.parseColor(it)) } }

        binding.groupStorage.removeAllViews()
        if (details.capacity.isNotEmpty()) { details.capacity.forEach { addCapacity(it) } }

        lifecycleScope.launch(Dispatchers.IO) {
            val adapter = ListDelegationAdapter(productImage {/* OnClick Event */})
            adapter.items = details.images

            val price = NumberFormat.getCurrencyInstance(Locale.US).format(details.price)

            withContext(Dispatchers.Main) {
                binding.imageCarousel.adapter = adapter
                binding.tvProductPrice.text = price
            }
        }
    }

    private fun addColor(color: Int) {
        val chip = layoutInflater.inflate(
            R.layout.item_color,
            binding.groupColors,
            false
        ) as Chip
        chip.chipIconTint = ColorStateList.valueOf(color)
        if (binding.groupColors.childCount == 0) chip.isChecked = true
        binding.groupColors.addView(chip)
    }

    private fun addCapacity(value: String) {
        val chip = layoutInflater.inflate(
            R.layout.item_capacity,
            binding.groupStorage,
            false
        ) as Chip

        chip.text = String.format(resources.getString(R.string.gb_value, value))
        if (binding.groupStorage.childCount == 0) chip.isChecked = true
        binding.groupStorage.addView(chip)
    }

    private fun setImageCarouselTransformations() {
        binding.imageCarousel.offscreenPageLimit = 3

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(
            MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt())
        )
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }

        binding.imageCarousel.setPageTransformer(compositePageTransformer)
    }

}