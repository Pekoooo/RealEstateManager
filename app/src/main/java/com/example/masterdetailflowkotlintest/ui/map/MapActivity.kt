package com.example.masterdetailflowkotlintest.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.math.log

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 123
        private const val TAG = "MyMapActivity"
    }

    private lateinit var binding: ActivityMapBinding
    private var location = Location("provider")
    private val viewModel: MapViewModel by viewModels()

    private val perms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.my_position)

        viewModel.getLocationLiveData().observe(this) {
            Log.d(TAG, "onCreate: location is updated to ${it.latitude} ${it.longitude}")
            location = it

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.google_map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        checkForPerms()
    }

    private fun checkForPerms() {
        if (hasLocationPermissions()) {
            viewModel.getUserLocation()
        } else {
            requestLocationPermissions()
        }
    }

    private fun hasLocationPermissions() =
        EasyPermissions.hasPermissions(
            this,
            *perms
        )

    private fun requestLocationPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale),
            LOCATION_PERMISSION_REQ_CODE,
            *perms
        )
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {

        p0.isMyLocationEnabled = true
        Log.d(TAG, "onMapReady: ${location.latitude} ${location.longitude}")
        val latLnt = LatLng(
            location.latitude,
            location.longitude
        )

        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLnt, 15F))
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.getUserLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermissions()
        }
    }


}