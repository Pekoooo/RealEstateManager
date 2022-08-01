package com.example.masterdetailflowkotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterdetailflowkotlintest.model.PropertyDetailedPicture

class PropertyDetailPictureAdapter : RecyclerView.Adapter<PropertyDetailPictureAdapter.ViewHolder>(){


    var detailPictures: MutableList<PropertyDetailedPicture> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        populatePictureList()
    }

    private fun populatePictureList(){
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-illustration/modern-white-wooden-kitchen-island-600w-2126341436.jpg", "kitchen"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/modern-luxury-design-brutal-apartment-600w-2160653193.jpg", "living room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/soundproof-home-theater-cellar-600w-230243485.jpg", "cave"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/soundproof-home-theater-cellar-600w-230243485.jpg", "cinema"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-illustration/modern-bright-minimalist-bedroom-orange-600w-1927563686.jpg", "video room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/kitchen-new-luxury-home-quartz-600w-1818540647.jpg", "mocha room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/chicago-il-usa-september-9-600w-1915460233.jpg", "cellar"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-illustration/modern-white-wooden-kitchen-island-600w-2126341436.jpg", "kitchen"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/modern-luxury-design-brutal-apartment-600w-2160653193.jpg", "living room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/soundproof-home-theater-cellar-600w-230243485.jpg", "cave"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/soundproof-home-theater-cellar-600w-230243485.jpg", "cinema"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-illustration/modern-bright-minimalist-bedroom-orange-600w-1927563686.jpg", "video room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/kitchen-new-luxury-home-quartz-600w-1818540647.jpg", "mocha room"))
        detailPictures.add(PropertyDetailedPicture("https://image.shutterstock.com/image-photo/chicago-il-usa-september-9-600w-1915460233.jpg", "cellar"))
    }



    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val interiorImage: ImageView = view.findViewById(R.id.interior_picture)
        val interiorPictureDescription: TextView = view.findViewById(R.id.picture_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_detail_view_property_picture, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPictureList: PropertyDetailedPicture = detailPictures[position]

        holder.interiorPictureDescription.text = currentPictureList.desc

        Glide.with(holder.interiorImage)
            .load(currentPictureList.url)
            .into(holder.interiorImage)

    }

    override fun getItemCount(): Int {
        return detailPictures.size
    }


}