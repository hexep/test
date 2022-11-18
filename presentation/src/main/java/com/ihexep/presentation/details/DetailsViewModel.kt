package com.ihexep.presentation.details

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.domain.usecases.GetPhoneDetailsUseCase
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(private val details: GetPhoneDetailsUseCase): ViewModel() {

    private val _state = MutableStateFlow<Resource<PhoneDetails>>(Resource.Loading)
    val state = _state.asStateFlow()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        viewModelScope.launch {
            details().collect { resource ->
                _state.value = resource
            }
        }
    }

}