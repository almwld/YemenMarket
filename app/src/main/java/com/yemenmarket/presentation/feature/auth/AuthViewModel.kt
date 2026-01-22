package com.yemenmarket.presentation.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            // TODO: Implement actual login logic
            if (email.isNotEmpty() && password.isNotEmpty()) {
                _isAuthenticated.value = true
            } else {
                _errorMessage.value = "البريد الإلكتروني أو كلمة المرور غير صحيحة"
            }
        }
    }
    
    fun register(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            // TODO: Implement actual registration logic
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                _isAuthenticated.value = true
            } else {
                _errorMessage.value = "يرجى ملء جميع الحقول"
            }
        }
    }
    
    fun logout() {
        _isAuthenticated.value = false
    }
}
