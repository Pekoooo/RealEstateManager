package com.example.masterdetailflowkotlintest.ui.addProperty

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.databinding.RowItemDetailViewPropertyPictureBinding

class AddPropertyAdapter(
    private val propertyPictureList: List<String>,
    private val onSelect: (String?) -> Unit
): RecyclerView.Adapter<AddPropertyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemDetailViewPropertyPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertyPicture = propertyPictureList[position]

        Glide.with(holder.propertyDetailPicture)
            .load(propertyPicture)
            .into(holder.propertyDetailPicture)
    }

    override fun getItemCount(): Int {
        return propertyPictureList.size
    }



    class ViewHolder (private val binding: RowItemDetailViewPropertyPictureBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(propertyPicture: String?, onSelect: (String?) -> Unit) {
                binding.root.setOnClickListener {
                    onSelect(propertyPicture)
                }
            }

        val propertyDetailPicture: ImageView = binding.propertyDetailPicture

    }


}