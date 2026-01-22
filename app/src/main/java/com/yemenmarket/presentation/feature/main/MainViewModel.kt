package com.yemen.market.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yemen.market.data.repository.auth.AuthRepository
import com.yemen.market.presentation.common.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _logoutEvent = MutableLiveData<Unit>()
    val logoutEvent: LiveData<Unit> = _logoutEvent

    fun logout() {
        authRepository.logout()
        _logoutEvent.value = Unit
    }
}
