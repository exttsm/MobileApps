package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {
    private lateinit var credentialsManager: CredentialsManager

    private val views by lazy {
        Views(
            email = requireView().findViewById(R.id.enterEmail),
            password = requireView().findViewById(R.id.enterPassword),
            loginButton = requireView().findViewById(R.id.nextButton),
            registerNow = requireView().findViewById(R.id.signUpTextView),
            forgotPassword = requireView().findViewById(R.id.forgetPassword)
        )
    }

    private data class Views(
        val email: EditText,
        val password: EditText,
        val loginButton: Button,
        val registerNow: TextView,
        val forgotPassword: TextView
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(views) {
            loginButton.setOnClickListener {
                if (validateLogin()) {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to RecipeListFragment after successful login
                    (activity as? MainActivity)?.navigateToFragment(RecipeListFragment.newInstance())
                } else {
                    Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                }
            }

            registerNow.setOnClickListener {
                (activity as? MainActivity)?.navigateToFragment(
                    RegisterFragment.newInstance(credentialsManager)
                )
            }

            forgotPassword.setOnClickListener {
                (activity as? MainActivity)?.navigateToFragment(ForgotPasswordFragment())
            }
        }
    }

    private fun validateLogin(): Boolean {
        with(views) {
            val enteredEmail = email.text.toString().trim()
            val enteredPassword = password.text.toString().trim()
            return credentialsManager.validateCredentials(enteredEmail, enteredPassword)
        }
    }

    companion object {
        fun newInstance(credentialsManager: CredentialsManager) = LoginFragment().apply {
            this.credentialsManager = credentialsManager
        }
    }
}