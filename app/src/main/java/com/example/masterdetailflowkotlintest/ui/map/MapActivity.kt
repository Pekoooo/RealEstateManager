package com.example.masterdetailflowkotlintest.ui.map

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 123
        private const val TAG = "MyMapActivity"
    }

    private lateinit var binding: ActivityMapBinding
    private val viewModel: MapViewModel by viewModels()

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


    override fun onMapReady(p0: GoogleMap) {

        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show()

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