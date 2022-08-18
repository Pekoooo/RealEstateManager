package com.example.masterdetailflowkotlintest.ui.addProperty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.ActivityAddPropertyBinding
import com.example.masterdetailflowkotlintest.model.Property
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.*
import kotlin.math.log

@AndroidEntryPoint
class AddPropertyActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MyAddPropertyActivity"
    }

    private val housingType: MutableList<String> = ArrayList()
    private val viewModel: AddPropertyViewModel by viewModels()

    private lateinit var binding: ActivityAddPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "New property"

        populateHousingTypeList()
        setUpSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.save -> {
                viewModel.createProperty(getPropertyInfo())
                //viewModel.createProperty(PlaceholderContent.ITEMS[(0..10).random()])
                viewModel.allProperties.observe(this){
                    Log.d(TAG, "onOptionsItemSelected: " + it[0].description)
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getPropertyInfo(): Property{

        return Property(
            1,
            binding.surfaceEditText.text.toString(),
            binding.spinner.selectedItem.toString(),
            binding.addressEditText.text.toString(),
            binding.cityEditText.text.toString(),
            binding.neighborhoodEditText?.text.toString(),
            binding.postalCodeEditText.text.toString(),
            binding.countryEditText.text.toString(),
            binding.priceEditText.text.toString(),
            binding.bathroomEditText.text.toString(),
            binding.bedroomEditText.text.toString(),
            Calendar.getInstance().time.toString(),
            binding.roomsEditText.text.toString(),
            binding.propertyDescriptionEditText.text.toString()
        )
    }

    private fun populateHousingTypeList() {
        housingType.add("House")
        housingType.add("Penthouse")
        housingType.add("Flat")
        housingType.add("Mansion")
    }

    private fun setUpSpinner() {
        val spinner = binding.spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, housingType)

        spinner.adapter = adapter

        binding.spinnerEditText.setOnClickListener {
            binding.spinner.performClick()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                (binding.spinnerEditText as TextView).text = binding.spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}




