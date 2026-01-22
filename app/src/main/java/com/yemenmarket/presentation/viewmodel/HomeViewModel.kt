package com.yemenmarket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yemenmarket.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    
    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            val result = getProductsUseCase()
            _loading.value = false
            if (result is com.yemenmarket.domain.model.Result.Success) {
                _products.value = result.data
            } else if (result is com.yemenmarket.domain.model.Result.Failure) {
                handleError(Exception(result.exception.message))
            }
        }
    }
}
