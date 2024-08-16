package com.example.furniq.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furniq.R
import com.example.furniq.data.get_all_products_data.PData

class CategoryByIdAdapter(private val listener : OnItemClickListener): RecyclerView.Adapter<CategoryByIdAdapter.MyViewHolder>() {

    var models: List<PData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKategoriya: TextView = itemView.findViewById(R.id.text_kategoriya_by_id)
        val starCount: TextView = itemView.findViewById(R.id.star_count_by_id)
        val commentCount: TextView = itemView.findViewById(R.id.comment_count_by_id)
        val price: TextView = itemView.findViewById(R.id.price_by_id)
        val img: ImageView = itemView.findViewById(R.id.img_in_card_by_id)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categories_by_id, parent, false)
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

            // Using Glide to load the image
            Glide.with(holder.itemView.context)
                .load(models[position].image.url)
                .into(img)
        }
    }

    override fun getItemCount(): Int = models.size

    interface OnItemClickListener {
        fun onItemClick(position: PData)
    }
}