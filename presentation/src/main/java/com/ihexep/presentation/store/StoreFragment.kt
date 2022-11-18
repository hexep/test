package com.ihexep.presentation.store

import com.ihexep.presentation.R
import com.ihexep.presentation.filter.FilterFragment
import com.ihexep.presentation.basket.BasketViewModel
import com.ihexep.presentation.databinding.FragmentStoreBinding
import com.ihexep.presentation.store.adapter.bestsellerList
import com.ihexep.presentation.store.adapter.homeStoreList
import com.ihexep.domain.common.Resource
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ihexep.presentation.store.adapter.categoryItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var categoriesAdapter: ListDelegationAdapter<List<Any>>
    private lateinit var productsAdapter: ListDelegationAdapter<List<Any>>

    private val storeViewModel by sharedViewModel<StoreViewModel>()
    private val basketViewModel by sharedViewModel<BasketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesAdapter = ListDelegationAdapter(categoryItem {})
        productsAdapter = ListDelegationAdapter(
            homeStoreList { findNavController().navigate(R.id.action_mainScreen_to_DetailsFragment) },
            bestsellerList { findNavController().navigate(R.id.action_mainScreen_to_DetailsFragment) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)

        binding.btnFilter.setOnClickListener {
            val filterBottomSheet = FilterFragment()
            filterBottomSheet.show(requireActivity().supportFragmentManager, FilterFragment.TAG)
        }

        binding.categoriesLayout.rvProductCategoryList.adapter = categoriesAdapter
        binding.rvProductList.adapter = productsAdapter

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.nav_basket)
                findNavController().navigate(R.id.action_mainScreen_to_basketFragment)
            true
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    storeViewModel.categoriesState.collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {}
                            is Resource.Loaded -> {
                                categoriesAdapter.items = resource.data
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
                launch {
                    storeViewModel.productState.collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {}
                            is Resource.Loaded -> {
                                productsAdapter.items = listOf(resource.data.homeStore, resource.data.bestSeller)
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
                launch {
                    basketViewModel.state.collect { state ->
                        when (state) {
                            is Resource.Loaded -> {
                                val menuItemId = binding.bottomNavigationView.menu.getItem(0).itemId
                                val badge: BadgeDrawable = binding.bottomNavigationView.getOrCreateBadge(menuItemId)
                                badge.isVisible = state.data.basket.isNotEmpty()
                                badge.number = state.data.basket.count()
                            }
                            is Resource.Loading -> { }
                            is Resource.Error -> { println(state.errorMessage) }
                        }
                    }
                }
            }
        }

        return binding.root
    }

}