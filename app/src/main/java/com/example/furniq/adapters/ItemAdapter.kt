package com.example.furniq.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furniq.R
import com.example.furniq.data.get_all_products_data.PData
import com.example.furniq.ui.power.favourites.HeartFragment

class ItemAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    var models: List<PData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val likedItems = mutableSetOf<Int>() // Set to track liked item IDs

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKategoriya: TextView = itemView.findViewById(R.id.text_kategoriya)
        val starCount: TextView = itemView.findViewById(R.id.star_count)
        val commentCount: TextView = itemView.findViewById(R.id.comment_count)
        val price: TextView = itemView.findViewById(R.id.price)
        val img: ImageView = itemView.findViewById(R.id.img_in_card)
        val btnSave: ImageView = itemView.findViewById(R.id.img_heart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_screen, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = models[position]
        holder.apply {
            // Sevimli holatiga ko'ra iconni yangilash
            if (likedItems.contains(data.id)) {
                btnSave.setImageResource(R.drawable.ic_saved) // "Saved" icon
            } else {
                btnSave.setImageResource(R.drawable.heart_svgrepo_com) // "Not Saved" icon
            }

            // btnSave tugmasi bosilganda hodisa
            btnSave.setOnClickListener {
                // Sevimli holatini tekshirish
                if (likedItems.contains(data.id)) {
                    likedItems.remove(data.id)
                    btnSave.setImageResource(R.drawable.heart_svgrepo_com) // Not Saved icon
                    listener.onRemoveFavorite(data) // Sevimlidan olib tashlash uchun ViewModelga yuborish
                } else {
                    likedItems.add(data.id)
                    btnSave.setImageResource(R.drawable.ic_saved) // Saved icon
                    listener.onAddFavorite(data) // Sevimliga qo'shish uchun ViewModelga yuborish
                }
            }

            textKategoriya.text = data.name.latin
            starCount.text = data.rating.toString()
            commentCount.text = data.reviews_count.toString()
            price.text = data.price.toString()

            // Using Glide to load the image
            Glide.with(holder.itemView.context)
                .load(data.image.url)
                .into(img)
        }
    }




    override fun getItemCount(): Int = models.size

    interface OnItemClickListener {
        fun onItemClick(position: PData)
        fun btnSaveClick(position: PData)

       fun onAddFavorite(position: PData)
       fun onRemoveFavorite(position: PData)


    }
}