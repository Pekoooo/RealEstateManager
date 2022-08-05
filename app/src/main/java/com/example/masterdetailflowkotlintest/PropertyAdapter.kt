package com.example.masterdetailflowkotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.databinding.ItemListContentBinding
import com.example.masterdetailflowkotlintest.model.Property

class PropertyAdapter(
    private val propertyList: List<Property>,
    private val itemDetailFragmentContainer: View?
) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = propertyList[position]
        holder.propertyLocation.text = property.neighborhood
        holder.propertyPrice.text = property.price
        holder.propertyType.text = property.type
        Glide.with(holder.propertyImage.context)
            .load(property.mainPhoto)
            .into(holder.propertyImage)

        with(holder.itemView) {
            tag = property
            setOnClickListener { itemView ->
                val item = itemView.tag as Property
                val bundle = Bundle()
                bundle.putString(
                    PropertyDetailFragment.ARG_ITEM_ID,
                    item.id.toString()
                )
                if (itemDetailFragmentContainer != null) {
                    itemDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                }
            }
        }
    }

    override fun getItemCount() = propertyList.size

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val propertyLocation: TextView = binding.propertyLocation
        val propertyPrice: TextView = binding.propertyPrice
        val propertyType: TextView = binding.propertyType
        val propertyImage: ImageView = binding.propertyImageView
    }

}