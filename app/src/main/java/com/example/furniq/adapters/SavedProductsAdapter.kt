package com.example.furniq.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furniq.R
import com.example.furniq.data.favourites_data.FData

class SavedProductsAdapter(private val listener : OnItemClickListener) : RecyclerView.Adapter<SavedProductsAdapter.MyViewHolder>(){

    var models: List<FData> = listOf()

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKategoriya: TextView = itemView.findViewById(R.id.text_kategoriya_saved)
        val starCount: TextView = itemView.findViewById(R.id.star_count_saved)
        val commentCount: TextView = itemView.findViewById(R.id.comment_count_saved)
        val price: TextView = itemView.findViewById(R.id.price_saved)
        val img: ImageView = itemView.findViewById(R.id.img_in_card_saved)
        val btnSave : ImageView = itemView.findViewById(R.id.img_heart_saved)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_products, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = models[position]
        holder.apply {

            itemView.setOnClickListener {

                listener.onItemClick(models[position])

            }

            textKategoriya.text = data.name.latin
            starCount.text = data.rating.toString()
            commentCount.text = data.reviews_count.toString()
            price.text = data.price.toString()

            Log.d("CCC", "onBindViewHolder---->adapter${data.price}: ")


            // Using Glide to load the image
            Glide.with(holder.itemView.context)
                .load(models[position].image.url)
                .into(img)
        }
    }

    override fun getItemCount(): Int = models.size

    interface OnItemClickListener {
        fun onItemClick(position: FData)
    }
}