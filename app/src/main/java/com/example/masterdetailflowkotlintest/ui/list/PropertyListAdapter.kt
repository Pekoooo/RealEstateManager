package com.example.masterdetailflowkotlintest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.databinding.RowPropertyListBinding
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.enums.CurrencyType

class PropertyListAdapter(
    private val propertyList: List<Property>,
    private val currencyType: CurrencyType = CurrencyType.DOLLAR,
    private val onSelect: (Property?) -> Unit
) : RecyclerView.Adapter<PropertyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPropertyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = propertyList[position]
        holder.propertyLocation.text = property.neighborhood

        when(currencyType){

            CurrencyType.DOLLAR -> {

                holder.propertyPrice.text = property.price.toString()
                holder.propertyPriceCurrency.text = "$"

            }

            CurrencyType.EURO -> {

                holder.propertyPrice.text = property.euroPrice
                holder.propertyPriceCurrency.text = "€"

            }

        }

        holder.propertyType.text = property.type
        holder.bind(propertyList[position], onSelect)

        if(property.isSold) holder.propertySaleStatus.text = "SOLD!" else holder.propertySaleStatus.text = "ON SALE!"

        Glide
            .with(holder.propertyImage)
            .load(property.mainPicture)
            .circleCrop()
            .into(holder.propertyImage)

    }

    override fun getItemCount() = propertyList.size

    class ViewHolder(private val binding: RowPropertyListBinding) :
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
        val propertySaleStatus: TextView = binding.propertySaleStatus
        val propertyPriceCurrency: TextView = binding.propertyPriceCurrency
    }


}