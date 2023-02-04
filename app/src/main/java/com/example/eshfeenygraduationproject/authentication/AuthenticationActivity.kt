package com.example.eshfeenygraduationproject.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eshfeenygraduationproject.R

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To change the splash screen theme to the default theme for the application
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        setContentView(R.layout.activity_authentication)
    }
}