package com.example.masterdetailflowkotlintest.ui.detail

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentItemDetailBinding
import com.example.masterdetailflowkotlintest.model.Property
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertyDetailFragment : Fragment() {

    companion object {
        const val ARG_ITEM_ID = "item_id"
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
        setRecyclerView()

        if (arguments?.containsKey(ARG_ITEM_ID) == true) {
            val id: Int? = arguments?.getInt(ARG_ITEM_ID)
            lifecycle.coroutineScope.launch{
                viewModel.getPropertyById(id!!).collect { updateContent(it) }
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
                    val id = arguments!!.getInt(ARG_ITEM_ID)
                    val args = Bundle()
                    args.putInt(ARG_ITEM_ID, id)
                    findNavController().navigate(R.id.addPropertyFragment, args)

                    true
                }
                else -> true
            }

        },viewLifecycleOwner)

    }

    private fun setRecyclerView(){
        val adapter = PropertyDetailPictureAdapter()
        val recyclerView: RecyclerView = binding.recyclerViewDetailedView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}