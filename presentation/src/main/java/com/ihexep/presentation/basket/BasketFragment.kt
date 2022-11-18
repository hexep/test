package com.ihexep.presentation.basket

import com.ihexep.presentation.R
import com.ihexep.presentation.basket.adapter.basketItem
import com.ihexep.presentation.databinding.FragmentBasketBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Basket
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.NumberFormat
import java.util.*

class BasketFragment : Fragment() {
    private lateinit var binding: FragmentBasketBinding
    private lateinit var adapter: ListDelegationAdapter<List<Any>>
    private val viewModel by sharedViewModel<BasketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ListDelegationAdapter(basketItem())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(inflater, container, false)

        binding.rvBasket.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is Resource.Loaded -> {
                            setScreenValues(state.data)
                        }
                        is Resource.Loading -> {}
                        is Resource.Error -> {
                            println(state.errorMessage)
                        }
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_basketFragment_to_mainScreen)
        }

        return binding.root
    }

    private fun setScreenValues(basket: Basket) {
        adapter.items = basket.basket

        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.maximumFractionDigits = 0

        binding.tvTotalPrice.text = String.format(
            resources.getString(R.string.us_value, formatter.format(basket.total))
        )
        binding.tvDeliveryPrice.text = basket.delivery
    }

}