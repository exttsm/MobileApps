package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {
    private lateinit var credentialsManager: CredentialsManager

    private val views by lazy {
        Views(
            fullName = requireView().findViewById(R.id.enterName),
            email = requireView().findViewById(R.id.enterEmail),
            phone = requireView().findViewById(R.id.enterNumber),
            password = requireView().findViewById(R.id.enterPassword),
            terms = requireView().findViewById(R.id.checkBox),
            signUpButton = requireView().findViewById(R.id.signUpButton),
            loginText = requireView().findViewById(R.id.logInTextView)
        )
    }

    private data class Views(
        val fullName: EditText,
        val email: EditText,
        val phone: EditText,
        val password: EditText,
        val terms: CheckBox,
        val signUpButton: Button,
        val loginText: TextView
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(views) {
            signUpButton.setOnClickListener {
                if (validateInputs()) {
                    val userData = UserData(
                        fullName = fullName.text.toString().trim(),
                        email = email.text.toString().trim(),
                        phoneNumber = phone.text.toString().trim(),
                        password = password.text.toString().trim()
                    )

                    credentialsManager.register(userData)
                        .onSuccess {
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                            // Navigate back to login
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        .onFailure { exception ->
                            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }

            loginText.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        with(views) {
            val fullNameStr = fullName.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val phoneStr = phone.text.toString().trim()
            val passwordStr = password.text.toString().trim()

            if (fullNameStr.isEmpty()) {
                fullName.error = "Full name is required"
                isValid = false
            }

            if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                email.error = "Valid email is required"
                isValid = false
            }

            if (phoneStr.isEmpty() || phoneStr.length < 10) {
                phone.error = "Valid phone number is required"
                isValid = false
            }

            if (passwordStr.isEmpty() || passwordStr.length < 6) {
                password.error = "Password must be at least 6 characters"
                isValid = false
            }

            if (!terms.isChecked) {
                Toast.makeText(context, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        return isValid
    }

    companion object {
        fun newInstance(credentialsManager: CredentialsManager) = RegisterFragment().apply {
            this.credentialsManager = credentialsManager
        }
    }
}
