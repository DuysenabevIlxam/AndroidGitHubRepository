package com.example.furniq.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furniq.R
import com.example.furniq.ui.auth.sign_in.Data
import com.example.furniq.ui.auth.sign_in.Demo
import com.example.furniq.ui.auth.sign_in.LData

class LatestAdapter(private val context: Context, private val listener : OnItemClickListener) : RecyclerView.Adapter<LatestAdapter.MyViewHolder>() {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("LikedItemsPrefs", Context.MODE_PRIVATE)

    var models: List<LData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val likedItems = mutableSetOf<Int>()


    init {
        // Load liked items from SharedPreferences
        likedItems.addAll(prefs.getStringSet("LIKED_ITEMS", emptySet())!!.map { it.toInt() })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKategoriya: TextView = itemView.findViewById(R.id.text_kategoriya_latest)
        val starCount: TextView = itemView.findViewById(R.id.star_count_latest)
        val commentCount: TextView = itemView.findViewById(R.id.comment_count_latest)
        val price: TextView = itemView.findViewById(R.id.price_latest)
        val img: ImageView = itemView.findViewById(R.id.img_in_card_latest)
        val btnSave: ImageView = itemView.findViewById(R.id.img_heart_latest)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_latest, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = models[position]
        holder.apply {
            itemView.setOnClickListener {
                listener.onItemClick(models[position])
            }

            if (likedItems.contains(data.id)) {
                btnSave.setImageResource(R.drawable.ic_saved) // "Saved" icon
            } else {
                btnSave.setImageResource(R.drawable.heart_svgrepo_com) // "Not Saved" icon
            }

            btnSave.setOnClickListener {
                if (likedItems.contains(data.id)) {
                    likedItems.remove(data.id)
                    btnSave.setImageResource(R.drawable.heart_svgrepo_com) // Update to "Not Saved" icon
                    listener.onRemoveFavorite(data)
                } else {
                    likedItems.add(data.id)
                    btnSave.setImageResource(R.drawable.ic_saved) // Update to "Saved" icon
                    listener.onAddFavorite(data)
                }
                saveLikedItems()
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

    private fun saveLikedItems() {
        // Convert likedItems to Set<String> for SharedPreferences
        prefs.edit().putStringSet("LIKED_ITEMS", likedItems.map { it.toString() }.toSet()).apply()
    }

    interface OnItemClickListener {
        fun onItemClick(position: LData)
        fun btnSaveClick(position: LData)
        fun onAddFavorite(position: Demo)
        fun onRemoveFavorite(position: Demo)
    }
}