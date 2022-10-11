package com.example.masterdetailflowkotlintest.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentItemDetailBinding
import com.example.masterdetailflowkotlintest.model.Photo
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.CurrencyType
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertyDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: PropertyDetailViewModel by viewModels()
    private val args: PropertyDetailFragmentArgs by navArgs()
    private lateinit var currentProperty: Property
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private var currencyType: CurrencyType? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        when(args.itemId){

                0 -> Log.d(MainActivity.TAG, "onViewCreated: id is 0, setting up special fragment ")

                else -> {
                    lifecycle.coroutineScope.launch {
                    viewModel.getPropertyById(args.itemId).collect {
                        currentProperty = it
                        observeCurrency()
                    }

                }
            }

        }



        val options = GoogleMapOptions()
            .liteMode(true)


        val mapFragment = parentFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)





        binding.soldButton?.setOnClickListener {

            if(!currentProperty.isSold) initConfirmationDialog() else Toast.makeText(requireContext(), "Property already sold", Toast.LENGTH_SHORT).show()

        }

        initMenuItems()

    }

    private fun observeCurrency() {

        viewModel.currencyType().observe(viewLifecycleOwner){

            when(it){
                CurrencyType.DOLLAR, null -> {
                    currencyType = CurrencyType.DOLLAR
                    updateContent(currentProperty)
                }
                CurrencyType.EURO -> {
                    currencyType = CurrencyType.EURO
                    updateContent(currentProperty)
                }

            }

        }
    }

    override fun onMapReady(p0: GoogleMap) {

    }


    private fun initConfirmationDialog() {

        val builder = AlertDialog.Builder(context)
            .create()
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.confirmation_property_sale_dialog, null)

        val confirmButton = dialogLayout.findViewById<Button>(R.id.confirm_button)
        val cancelButton = dialogLayout.findViewById<Button>(R.id.cancel_button)

        builder.setView(dialogLayout)
        builder.show()

        confirmButton.setOnClickListener {

            viewModel.updateProperty(currentProperty.copy(isSold = true))
            builder.dismiss()

        }

        cancelButton.setOnClickListener { builder.dismiss() }

    }


    private fun setRecyclerView(
        recyclerView: RecyclerView,
        propertyList: MutableList<Photo>

    ) {


        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PropertyDetailPictureAdapter(propertyList) {


            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.property_picture_zoom_dialog, null)

            Glide
                .with(requireContext())
                .load(it?.path)
                .override(1200, 1200)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(dialogLayout.findViewById(R.id.image_view_custom_dialog))


            builder.setTitle(it?.description)
            builder.setView(dialogLayout)
            builder.show()

        }

    }

    private fun updateContent(property: Property) {

        property.let {


            when(currencyType){
                CurrencyType.DOLLAR, null -> {
                    binding.titleTextView.text = property.toString()
                    binding.currencySymbol?.text = "$"
                }
                CurrencyType.EURO -> {
                    binding.titleTextView.text = property.toStringEuroPrice
                    binding.currencySymbol?.text = "€"
                }

            }

            binding.propertySurface.text = property.surface
            binding.propertyAddress.text = property.address
            binding.propertyCity.text = property.city
            binding.propertyPostalCode.text = property.postalCode
            binding.propertyCountry.text = property.city
            binding.propertyBathrooms.text = property.bathrooms
            binding.propertyBedrooms.text = property.bedrooms
            binding.createdAtDate.text = property.listedAt
            binding.propertyRoom.text = property.rooms
            binding.propertyCountry.text = property.country
            binding.propertyDescription.text = property.description

            displayPoiAsChips(property)

            setRecyclerView(
                binding.recyclerViewDetailedView,
                property.pictureList
            )

            if(property.isSold) binding.soldButton?.text = resources.getText(R.string.sold)
        }
    }

    private fun displayPoiAsChips(property: Property) {

        binding.propertyPoiChipgroup?.removeAllViews()
        binding.propertyPoiChipgroup?.addView(viewModel.getPoiChipGroup(property, context))

    }

    private fun initMenuItems() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_detail_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.edit -> {
                    if(!currentProperty.isSold) {

                        val action =
                            PropertyDetailFragmentDirections.actionItemDetailFragmentToAddPropertyFragment(args.itemId)


                        findNavController().navigate(action)

                    } else Toast.makeText(requireContext(), "Property sold", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> true
            }

        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}