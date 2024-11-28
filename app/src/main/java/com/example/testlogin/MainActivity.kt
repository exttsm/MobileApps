package com.example.testlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var registerNowTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailEditText = findViewById(R.id.enterEmail)
        passwordEditText = findViewById(R.id.enterPassword)
        nextButton = findViewById(R.id.nextButton)
        registerNowTextView = findViewById(R.id.signUpTextView)

        // Login button logic
        nextButton.setOnClickListener {
            if (validateLogin()) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                // Add any post-login actions here
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigation to Sign Up screen
        registerNowTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateLogin(): Boolean {
        val enteredEmail = emailEditText.text.toString().trim()
        val enteredPassword = passwordEditText.text.toString().trim()
        return enteredEmail == "test@te.st" && enteredPassword == "1234"
    }
}