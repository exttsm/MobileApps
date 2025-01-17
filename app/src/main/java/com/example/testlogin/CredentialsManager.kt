package com.example.testlogin

class CredentialsManager {
    private val credentials = mutableMapOf<String, UserData>()

    init {
        // Add test credentials
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
        return credentials[email]?.password == password
    }
}