package com.ihexep.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihexep.presentation.databinding.FragmentFilterBinding
import com.ihexep.presentation.store.StoreViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel by sharedViewModel<StoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        binding.closeButton.setOnClickListener { dismiss() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    // Fill brands spinner
                    viewModel.brands.collect {
                        val brandsAdapter = ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_spinner_item,
                            listOf("None").plus(it)
                        )
                        brandsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        withContext(Dispatchers.Main) {
                            binding.spinnerBrand.adapter = brandsAdapter
                        }
                    }
                }
                launch {
                    // Fill price spinner
                    viewModel.priceRanges.collect {
                        val pricesAdapter = ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_spinner_item,
                            listOf("None").plus(it)
                        )
                        pricesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        withContext(Dispatchers.Main) {
                            binding.spinnerPrice.adapter = pricesAdapter
                        }
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}