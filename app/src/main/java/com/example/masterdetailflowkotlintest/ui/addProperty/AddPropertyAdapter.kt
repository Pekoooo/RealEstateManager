package com.example.masterdetailflowkotlintest.ui.addProperty

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.databinding.RowItemDetailViewPropertyPictureBinding
import com.example.masterdetailflowkotlintest.databinding.RowItemDetailViewPropertyPictureEditBinding
import com.example.masterdetailflowkotlintest.model.Photo

class AddPropertyAdapter(
    private val propertyPictureList: List<Photo>,
    private val onSelect: (Photo?, Int) -> Unit
): RecyclerView.Adapter<AddPropertyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowItemDetailViewPropertyPictureEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertyPicture = propertyPictureList[position]

        holder.bind(propertyPictureList[position], onSelect)

        Glide.with(holder.propertyDetailPicture)
            .load(propertyPicture.path)
            .into(holder.propertyDetailPicture)

        holder.propertyPictureDescription.text = propertyPicture.description


    }

    override fun getItemCount(): Int {
        return propertyPictureList.size
    }


    class ViewHolder (private val binding: RowItemDetailViewPropertyPictureEditBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(propertyPicture: Photo?, onSelect: (Photo?, Int) -> Unit) {
                binding.root.setOnClickListener {
                    onSelect(propertyPicture, bindingAdapterPosition)
                }
            }

        val propertyDetailPicture: ImageView = binding.propertyDetailPicture
        val propertyPictureDescription: TextView = binding.propertyDetailPictureDescription

    }


}