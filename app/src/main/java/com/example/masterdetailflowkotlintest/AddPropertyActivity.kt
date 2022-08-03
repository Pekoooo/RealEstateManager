package com.example.masterdetailflowkotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.masterdetailflowkotlintest.databinding.ActivityAddPropertyBinding


class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}