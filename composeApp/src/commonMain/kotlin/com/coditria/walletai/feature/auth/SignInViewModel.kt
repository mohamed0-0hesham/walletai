package com.coditria.walletai.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class SignInState(
    val email: String = "hisham@walletai.eg",
    val password: String = "password123",
    val passwordVisible: Boolean = false,
    val rememberMe: Boolean = true,
)

class SignInViewModel {
    var state: SignInState by mutableStateOf(SignInState())
        private set

    fun onEmailChange(value: String) { state = state.copy(email = value) }
    fun onPasswordChange(value: String) { state = state.copy(password = value) }
    fun onTogglePasswordVisible() { state = state.copy(passwordVisible = !state.passwordVisible) }
    fun onToggleRemember() { state = state.copy(rememberMe = !state.rememberMe) }
}
