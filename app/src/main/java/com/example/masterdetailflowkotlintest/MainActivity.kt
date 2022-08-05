package com.example.masterdetailflowkotlintest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.masterdetailflowkotlintest.databinding.ActivityItemDetailBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        val binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.title = R.string.app_name.toString()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(applicationContext, MapActivity::class.java)

        return when (item.itemId){

            R.id.map -> {
                startActivity(intent)
             true
             }

            R.id.currency ->{
                Toast.makeText(applicationContext, "Changing currency", Toast.LENGTH_LONG).show()
                return true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}


