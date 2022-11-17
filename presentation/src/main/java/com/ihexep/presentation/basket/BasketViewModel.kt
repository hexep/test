package com.ihexep.presentation.basket

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.Basket
import com.ihexep.domain.usecases.GetBasketUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasketViewModel(private val getBasketUseCase: GetBasketUseCase): ViewModel() {

    private val _state = MutableStateFlow<Resource<Basket>>(Resource.Loading)
    val state = _state.asStateFlow()

    init {
        getBasket()
    }

    private fun getBasket() {
        viewModelScope.launch {
            getBasketUseCase().collect { resource ->
                _state.value = resource
            }
        }
    }
}