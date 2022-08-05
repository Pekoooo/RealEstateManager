package com.example.masterdetailflowkotlintest

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.masterdetailflowkotlintest.MapActivity.Companion.TAG
import com.example.masterdetailflowkotlintest.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.tasks.Task
import pub.devrel.easypermissions.EasyPermissions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        private const val LOCATION_PERMISSION_REQ_CODE = 123
        private const val TAG = "MyMapActivity"
    }

    private lateinit var binding: ActivityMapBinding
    private val perms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My position"


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show()

    }




}