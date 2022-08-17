package com.example.masterdetailflowkotlintest.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailflowkotlintest.databinding.FragmentItemDetailBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.model.PropertyDetailedPicture
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent


class PropertyDetailFragment : Fragment() {

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    private var property: Property? = null
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context as AppCompatActivity).supportActionBar!!.title = "Detail"

        if (arguments?.containsKey(ARG_ITEM_ID) == true) {
            val id: Int? = arguments?.getInt(ARG_ITEM_ID)
            property = PlaceholderContent.ITEM_MAP[id]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateContent()
        setRecyclerView()
    }

    private fun setRecyclerView(){
        val adapter = PropertyDetailPictureAdapter()
        val recyclerView: RecyclerView = binding.recyclerViewDetailedView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val testList: MutableList<PropertyDetailedPicture> = ArrayList()
    }

    private fun updateContent() {
        property?.let {
            binding.propertySurface.text = property!!.surface
            binding.propertyAddress.text = property!!.address
            binding.propertyCity.text = property!!.city
            binding.propertyPostalCode.text = property!!.postalCode
            binding.propertyCountry.text = property!!.city
            binding.propertyBathrooms.text = property!!.bathrooms
            binding.propertyBedrooms.text = property!!.bedrooms
            binding.createdAtDate.text = property!!.listedAt
            binding.propertyRoom.text = property!!.rooms
            binding.propertyCountry.text = property!!.country
            binding.propertyDescription.text = property!!.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}