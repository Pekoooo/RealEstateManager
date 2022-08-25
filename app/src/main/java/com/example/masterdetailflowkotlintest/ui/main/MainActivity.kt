package com.example.masterdetailflowkotlintest.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainActivityToolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        val navController = findNavController(R.id.fragment_nav_host)
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)

    }


}


