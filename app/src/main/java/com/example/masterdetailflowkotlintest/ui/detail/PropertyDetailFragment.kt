package com.example.masterdetailflowkotlintest.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentItemDetailBinding
import com.example.masterdetailflowkotlintest.model.Photo
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertyDetailFragment : Fragment() {

    companion object {
        const val ARG_ITEM_ID = "item_id"
        const val TAG = "MyPropertyDetail"
    }

    private val viewModel: PropertyDetailViewModel by viewModels()
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments?.containsKey(ARG_ITEM_ID) == true) {
            val id: Int? = arguments?.getInt(ARG_ITEM_ID)
            lifecycle.coroutineScope.launch{
                viewModel.getPropertyById(id!!).collect {
                    updateContent(it)
                }
            }
        }

        requireActivity().addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_detail_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean
            = when (menuItem.itemId){

                R.id.edit -> {
                    val id = requireArguments().getInt(ARG_ITEM_ID)
                    val args = Bundle()
                    args.putInt(ARG_ITEM_ID, id)
                    findNavController().navigate(R.id.addPropertyFragment, args)

                    true
                }
                else -> true
            }

        },viewLifecycleOwner)

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
                .override(1200,1200)
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
            binding.titleTextView.text = property.toString()
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

            setRecyclerView(
                binding.recyclerViewDetailedView,
                property.pictureList
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}