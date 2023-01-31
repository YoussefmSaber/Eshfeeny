package com.example.eshfeenygraduationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To change the splash screen theme to the default theme for the application
        setTheme(R.style.Theme_EshfeenyGraduationProject)

        setContentView(R.layout.activity_main)
    }
}