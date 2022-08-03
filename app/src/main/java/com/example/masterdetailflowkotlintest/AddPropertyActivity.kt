package com.example.masterdetailflowkotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.masterdetailflowkotlintest.databinding.ActivityAddPropertyBinding
import com.example.masterdetailflowkotlintest.databinding.TestActivityAddPropertyBinding

class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding: TestActivityAddPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}