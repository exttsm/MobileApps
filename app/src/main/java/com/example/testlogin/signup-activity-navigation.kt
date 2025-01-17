package com.example.testlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var signUpButton: Button
    private lateinit var logInTextView: TextView
    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        fullNameEditText = findViewById(R.id.enterName)
        emailEditText = findViewById(R.id.enterEmail)
        phoneEditText = findViewById(R.id.enterNumber)
        passwordEditText = findViewById(R.id.enterPassword)
        termsCheckBox = findViewById(R.id.checkBox)
        signUpButton = findViewById(R.id.signUpButton)
        logInTextView = findViewById(R.id.logInTextView)

        // Sign Up button logic
        signUpButton.setOnClickListener {
            if (validateInputs()) {
                val userData = UserData(
                    fullName = fullNameEditText.text.toString().trim(),
                    email = emailEditText.text.toString().trim(),
                    phoneNumber = phoneEditText.text.toString().trim(),
                    password = passwordEditText.text.toString().trim()
                )

                credentialsManager.register(userData)
                    .onSuccess {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        // Navigate back to login screen
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()  // Close the registration activity
                    }
                    .onFailure { exception ->
                        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Navigation back to Login screen
        logInTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (fullName.isEmpty()) {
            fullNameEditText.error = "Full name is required"
            isValid = false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Valid email is required"
            isValid = false
        }

        if (phone.isEmpty() || phone.length < 10) {
            phoneEditText.error = "Valid phone number is required"
            isValid = false
        }

        if (password.isEmpty() || password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            isValid = false
        }

        if (!termsCheckBox.isChecked) {
            Toast.makeText(this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}