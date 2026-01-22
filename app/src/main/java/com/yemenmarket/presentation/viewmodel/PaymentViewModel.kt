package com.yemenmarket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    
    private val _paymentStatus = MutableStateFlow<PaymentStatus>(PaymentStatus.Idle)
    val paymentStatus: StateFlow<PaymentStatus> = _paymentStatus
    
    private val _selectedMethod = MutableStateFlow("bank")
    val selectedMethod: StateFlow<String> = _selectedMethod
    
    fun selectPaymentMethod(method: String) {
        _selectedMethod.value = method
    }
    
    fun processPayment(amount: Double, method: String) {
        viewModelScope.launch {
            _paymentStatus.value = PaymentStatus.Processing
            
            // Simulate payment processing
            kotlinx.coroutines.delay(2000)
            
            if (method == "bank") {
                _paymentStatus.value = PaymentStatus.BankTransfer(
                    bankName = "البنك الأهلي اليمني",
                    accountNumber = "YE1234567890123456789012",
                    accountName = "Yemen Market Store",
                    amount = amount
                )
            } else {
                _paymentStatus.value = PaymentStatus.Success("تم الدفع بنجاح عبر $method")
            }
        }
    }
}

sealed class PaymentStatus {
    object Idle : PaymentStatus()
    object Processing : PaymentStatus()
    data class BankTransfer(
        val bankName: String,
        val accountNumber: String,
        val accountName: String,
        val amount: Double
    ) : PaymentStatus()
    data class Success(val message: String) : PaymentStatus()
    data class Error(val message: String) : PaymentStatus()
}
