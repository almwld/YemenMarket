package com.yemenmarket.presentation.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    
    protected val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    protected fun handleError(exception: Exception) {
        _error.value = exception.message ?: "حدث خطأ غير معروف"
    }
}
