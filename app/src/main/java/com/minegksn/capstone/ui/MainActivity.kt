package com.minegksn.capstone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}