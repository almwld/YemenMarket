package com.yemen.market.presentation.feature.splash

import com.yemen.market.data.repository.auth.AuthRepository
import com.yemen.market.presentation.common.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun checkUserState(callback: (Boolean, String?) -> Unit) {
        launch {
            val isLoggedIn = authRepository.isUserLoggedIn()
            val userType = if (isLoggedIn) authRepository.getCurrentUserType() else null
            callback(isLoggedIn, userType)
        }
    }
}
