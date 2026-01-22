package com.yemenmarket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages
    
    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending
    
    fun sendMessage(text: String) {
        viewModelScope.launch {
            _isSending.value = true
            
            val newMessage = ChatMessage(
                id = System.currentTimeMillis().toString(),
                text = text,
                isMe = true,
                timestamp = System.currentTimeMillis()
            )
            
            _messages.value = _messages.value + newMessage
            
            // Simulate reply
            kotlinx.coroutines.delay(1000)
            
            val replyMessage = ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                text = "شكراً لرسالتك، سنرد عليك قريباً",
                isMe = false,
                timestamp = System.currentTimeMillis()
            )
            
            _messages.value = _messages.value + replyMessage
            _isSending.value = false
        }
    }
}

data class ChatMessage(
    val id: String,
    val text: String,
    val isMe: Boolean,
    val timestamp: Long
)
