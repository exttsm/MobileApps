package com.example.testlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CredentialsManager {
    private val credentials = mutableMapOf<String, UserData>()
    private val _loginState = MutableStateFlow<Boolean>(false)
    val loginState: StateFlow<Boolean> = _loginState.asStateFlow()

    init {
        credentials["test@te.st"] = UserData(
            fullName = "Test User",
            email = "test@te.st",
            phoneNumber = "1234567890",
            password = "1234"
        )
    }

    fun register(userData: UserData): Result<Unit> = runCatching {
        if (credentials.containsKey(userData.email)) {
            throw IllegalArgumentException("Email already registered")
        }
        credentials[userData.email] = userData
    }

    fun validateCredentials(email: String, password: String): Boolean {
        val isValid = credentials[email]?.password == password
        if (isValid) {
            _loginState.value = true
        }
        return isValid
    }

    fun logout() {
        _loginState.value = false
    }
}