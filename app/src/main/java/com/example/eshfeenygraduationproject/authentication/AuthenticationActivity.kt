package com.example.eshfeenygraduationproject.authentication


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel


class AuthenticationActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        // To change the splash screen theme to the default theme for the application
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                Log.i("test activity", viewModel.loadingToLogin.value!!.toString())
                !(viewModel.loadingToLogin.value!!)
            }
        }
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        setContentView(R.layout.activity_authentication)
    }
}
