package com.example.eshfeenygraduationproject.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entity.VerifyLoginResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.eshfeeny.EshfeenyActivity

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To change the splash screen theme to the default theme for the application
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        setContentView(R.layout.activity_authentication)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        viewModel.getUserData()
        viewModel.userData.observe(this) { userData ->
            val userEmail = userData.email
            val userPassword = userData.password
            val userCredintals = VerifyLoginResponse(userEmail, userPassword)
            viewModel.verifyLogin(userCredintals)
            viewModel.verifyUserLogin.observe(this) {
                if (it != null) {
                    val intent = Intent(
                        this,
                        EshfeenyActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
    }
}
