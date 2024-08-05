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
import com.example.furniq.data.popular_data.Data

class PopularAdapter(): RecyclerView.Adapter<PopularAdapter.MyViewHolder>() {

    var models: List<Data> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKategoriya: TextView = itemView.findViewById(R.id.text_kategoriya_popular)
        val starCount: TextView = itemView.findViewById(R.id.star_count_popular)
        val commentCount: TextView = itemView.findViewById(R.id.comment_count_popular)
        val price: TextView = itemView.findViewById(R.id.price_popular)
        val img: ImageView = itemView.findViewById(R.id.img_in_card_popular)

        init {
            itemView.setOnClickListener {
                (itemView.context as? OnItemClickListener)?.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_products, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = models[position]
        holder.apply {
            textKategoriya.text = data.name.latin
            starCount.text = data.rating.toString()
            commentCount.text = data.reviews_count.toString()
            price.text = data.price.toString()
            Log.d("RRR", "adapterge keldi------->: ")


            // Using Glide to load the image
            Glide.with(holder.itemView.context)
                .load(models[position].image.url)
                .into(img)
        }
    }


    override fun getItemCount(): Int = models.size
}