package com.example.eshfeenygraduationproject.eshfeeny

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eshfeenygraduationproject.R

class EshfeenyActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To change the splash screen theme to the default theme for the application
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        setContentView(R.layout.activity_eshfeeny)
    }
}