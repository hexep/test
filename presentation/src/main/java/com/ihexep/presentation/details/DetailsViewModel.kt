package com.ihexep.presentation.details

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.PhoneDetails
import com.ihexep.domain.usecases.GetPhoneDetailsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(private val getDetails: GetPhoneDetailsUseCase): ViewModel() {

    private val _details = MutableStateFlow<Resource<PhoneDetails>>(Resource.Loading)
    val details = _details.asStateFlow()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        viewModelScope.launch {
            getDetails().collect { resource ->
                _details.value = resource
            }
        }
    }

}