package com.ihexep.presentation.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Phones
import com.ihexep.domain.model.ProductCategory
import com.ihexep.domain.usecases.GetCategoriesUseCase
import com.ihexep.domain.usecases.GetPhonesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoreViewModel(
    private val categories: GetCategoriesUseCase,
    private val phones: GetPhonesUseCase
    ): ViewModel() {

    private val _categoriesState = MutableStateFlow<Resource<List<ProductCategory>>>(Resource.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private val _productState = MutableStateFlow<Resource<Phones>>(Resource.Loading)
    val productState = _productState.asStateFlow()

    private val _brands = MutableStateFlow<List<String>>(emptyList())
    val brands = _brands.asStateFlow()

    private val _priceRanges = MutableStateFlow<List<String>>(emptyList())
    val priceRanges = _priceRanges.asStateFlow()

    init {
        getCategories()
        getAllCellphones()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categories().collect { resource ->
                _categoriesState.value = resource
            }
        }
    }

    private fun getAllCellphones() {
        viewModelScope.launch {
            phones().collect { resource ->
                _productState.value = resource
                if (resource is Resource.Loaded) {
                    setBrands(resource.data)
                    setPriceRanges()
                }
            }
        }
    }

    private fun setBrands(data: Phones) {
        viewModelScope.launch(Dispatchers.IO) {
            val homeStoreBrands = data.homeStore.map {
                it.title.split(' ').first()
            }

            val bestSellerBrands = data.bestSeller.map {
                it.title.split(' ').first()
            }

            _brands.value = homeStoreBrands.plus(bestSellerBrands).distinct()
        }
    }

    private fun setPriceRanges() {
        viewModelScope.launch((Dispatchers.IO)) {
            val priceValues = arrayListOf<IntArray>()
            for (i in 0..9999 step 500) { priceValues.add(intArrayOf(i, i+500)) }
            _priceRanges.value = priceValues.map { "$${it[0]} - $${it[1]}" }
        }
    }

}