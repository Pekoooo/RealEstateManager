package com.example.masterdetailflowkotlintest.ui.map

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentMapBinding
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 123
        private const val TAG = "MyMapFragment"
    }

    private lateinit var binding: FragmentMapBinding
    private var location = Location("provider")
    private val viewModel: MapViewModel by viewModels()

    private val perms = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getLocationLiveData().observe(this) {
            location = it

            val mapFragment = childFragmentManager
                .findFragmentById(R.id.google_map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        checkForPerms()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.title = "Map"

        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
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
            requireContext(),
            *perms
        )

    private fun requestLocationPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_location),
            LOCATION_PERMISSION_REQ_CODE,
            *perms
        )
    }

    @RequiresApi(33)
    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        p0.isMyLocationEnabled = true
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