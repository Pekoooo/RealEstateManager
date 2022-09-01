package com.example.masterdetailflowkotlintest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.ItemListContentBinding
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.detail.PropertyDetailFragment

class PropertyAdapter(
    private val propertyList: List<Property>,
    private val itemDetailFragmentContainer: View?,
    private val onSelect: (Property?) -> Unit
) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = propertyList[position]
        holder.propertyLocation.text = property.neighborhood
        holder.propertyPrice.text = property.price
        holder.propertyType.text = property.type
        holder.bind(propertyList[position], onSelect)

    }

    override fun getItemCount() = propertyList.size

    class ViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property?, onSelect: (Property?) -> Unit) {
            binding.root.setOnClickListener {
                onSelect(property)
            }
        }

        val propertyLocation: TextView = binding.propertyLocation
        val propertyPrice: TextView = binding.propertyPrice
        val propertyType: TextView = binding.propertyType
        val propertyImage: ImageView = binding.propertyImageView
    }


}