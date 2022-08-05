package com.example.masterdetailflowkotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportActionBar?.title = "My position"
    }
}