package com.ihexep.presentation.store

import com.ihexep.presentation.R
import com.ihexep.presentation.filter.FilterFragment
import com.ihexep.presentation.basket.BasketViewModel
import com.ihexep.presentation.databinding.FragmentStoreBinding
import com.ihexep.presentation.store.adapter.bestsellerList
import com.ihexep.presentation.store.adapter.categoryList
import com.ihexep.presentation.store.adapter.homeStoreList
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Categories
import com.ihexep.domain.model.ProductCategory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var adapter: ListDelegationAdapter<List<Any>>
    private val storeViewModel by sharedViewModel<StoreViewModel>()
    private val basketViewModel by sharedViewModel<BasketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ListDelegationAdapter(
            categoryList(),
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

        binding.rvProductList.adapter = adapter

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.nav_basket)
                findNavController().navigate(R.id.action_mainScreen_to_basketFragment)
            true
        }

        lifecycleScope.launch(Dispatchers.IO) {
            storeViewModel.state.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Loaded -> {
                        val list = mutableListOf<Any>()

                        list.add(Categories.values().map {
                            ProductCategory(
                                it.id,
                                it.title,
                                it.imageUrl
                            )
                        })
                        list.add(resource.data.homeStore)
                        list.add(resource.data.bestSeller)

                        adapter.items = list

                        withContext(Dispatchers.Main) {
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }

        lifecycleScope.launch {
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

        return binding.root
    }

}