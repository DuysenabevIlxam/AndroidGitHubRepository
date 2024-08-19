package com.example.furniq.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furniq.R
import com.example.furniq.data.get_products_by_id.ImageX
import com.example.furniq.databinding.ItemViewPagerImagesBinding

class ImagePagerAdapter(private val imageUrls: List<ImageX>) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemViewPagerImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: ImageX) {
            Glide.with(binding.root.context)
                .load(imageUrl.url)
                .into(binding.imgViewPagerItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemViewPagerImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size
}
