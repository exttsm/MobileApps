package com.example.testlogin

data class UserData(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)

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

    fun validateCredentials(email: String, password: String): Boolean {
        return credentials[email]?.password == password
    }

    fun register(userData: UserData): Result<Unit> {
        return if (credentials.containsKey(userData.email)) {
            Result.failure(IllegalArgumentException("Email already registered"))
        } else {
            credentials[userData.email] = userData
            Result.success(Unit)
        }
    }
}