package com.example.testlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val credentialsManager: CredentialsManager by lazy {
        RecipeApp.getInstance().credentialsManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            observeLoginState()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                credentialsManager.loginState.collect { isLoggedIn ->
                    navigateBasedOnLoginState(isLoggedIn)
                }
            }
        }
    }

    private fun navigateBasedOnLoginState(isLoggedIn: Boolean) {
        val fragment = if (isLoggedIn) {
            RecipeListFragment.newInstance()
        } else {
            LoginFragment.newInstance(credentialsManager)
        }

        replaceFragment(fragment)
    }

    fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}