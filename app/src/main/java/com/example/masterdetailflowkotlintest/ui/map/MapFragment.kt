package com.example.masterdetailflowkotlintest.ui.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentMapBinding
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.BitmapFromVector
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 123
        private const val TAG = "MyMapFragment"
    }

    private lateinit var binding: FragmentMapBinding
    private var location = Location("provider")
    private lateinit var allProperties: List<Property>
    private lateinit var gMap: GoogleMap
    private val viewModel: MapViewModel by viewModels()

    private val perms = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.allProperties.observe(this) {
            allProperties = it

            observeLocation()
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


    private fun observeLocation() {
        viewModel.getLocationLiveData().observe(this) {
            location = it

            val mapFragment = childFragmentManager
                .findFragmentById(R.id.google_map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    private fun setPropertiesMarker(allProperties: List<Property>) {
        Log.d(TAG, "setPropertiesMarker: $allProperties")

        allProperties.forEach {

            when (it.isSold) {

                true -> {
                    gMap.addMarker(
                        MarkerOptions()
                            .position(it.latLng)
                            .icon(
                                BitmapFromVector.BitmapFromVector(
                                    requireContext(),
                                    R.drawable.ic_house_sold
                                )
                            )
                    )
                }

                false -> {
                    gMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(it.lat!!, it.lng!!))
                            .icon(
                                BitmapFromVector.BitmapFromVector(
                                    requireContext(),
                                    R.drawable.ic_house_for_sale
                                )
                            )
                    )
                }
            }

        }


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
        gMap = p0
        p0.isMyLocationEnabled = true
        val latLnt = LatLng(
            location.latitude,
            location.longitude
        )

        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLnt, 15F))

        setPropertiesMarker(allProperties)

        gMap.setOnMarkerClickListener {
            val clickedProperty = viewModel.getPropertyWithLatLng(it.position)

            val action =
                MapFragmentDirections.actionMapFragmentToItemDetailFragment(clickedProperty!!.id)
            findNavController().navigate(action)

            true
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        findNavController().navigate(R.id.mapFragment)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermissions()
        }
    }


}