package com.practicum.myhabitreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.practicum.myhabitreminder.databinding.ActivityMainBinding
import com.practicum.myhabitreminder.presentation.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, LoginFragment())
                .commit()
        }
    }
}