package com.example.masterdetailflowkotlintest.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.databinding.RowItemDetailViewPropertyPictureBinding
import com.example.masterdetailflowkotlintest.model.Photo

class PropertyDetailPictureAdapter(

    propertyList: MutableList<Photo>,
    private val onSelect: (Photo?) -> Unit

) : RecyclerView.Adapter<PropertyDetailPictureAdapter.ViewHolder>() {


    private var detailPictures: MutableList<Photo> = propertyList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowItemDetailViewPropertyPictureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPictureList = detailPictures[position]

        holder.bind(detailPictures[position], onSelect)

        Glide.with(holder.propertyPhoto)
            .load(currentPictureList.path)
            .into(holder.propertyPhoto)

    }

    override fun getItemCount(): Int {
        return detailPictures.size
    }

    class ViewHolder(private val binding: RowItemDetailViewPropertyPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(propertyPhoto: Photo?, onSelect: (Photo?) -> Unit) {
            binding.root.setOnClickListener { onSelect(propertyPhoto) }
        }

        val propertyPhoto: ImageView = binding.propertyDetailPicture


    }


}