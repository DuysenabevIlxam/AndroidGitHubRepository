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
import com.example.furniq.ui.auth.sign_in.Demo
import com.example.furniq.ui.auth.sign_in.PData

class ItemAdapter(
    private val context: Context, // Context to access SharedPreferences
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("LikedItemsPrefs", Context.MODE_PRIVATE)

    var models: List<PData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val likedItems = mutableSetOf<Int>() // Set to track liked item IDs

    init {
        // Load liked items from SharedPreferences
        likedItems.addAll(prefs.getStringSet("LIKED_ITEMS", emptySet())!!.map { it.toInt() })
    }

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
            // Update icon according to the liked state
            if (likedItems.contains(data.id)) {
                btnSave.setImageResource(R.drawable.ic_saved) // "Saved" icon
            } else {
                btnSave.setImageResource(R.drawable.heart_svgrepo_com) // "Not Saved" icon
            }

            // btnSave button click event
            btnSave.setOnClickListener {
                if (likedItems.contains(data.id)) {
                    likedItems.remove(data.id)
                    btnSave.setImageResource(R.drawable.heart_svgrepo_com) // Not Saved icon
                    listener.onRemoveFavorite(data) // Send to ViewModel for removal from favorites
                } else {
                    likedItems.add(data.id)
                    btnSave.setImageResource(R.drawable.ic_saved) // Saved icon
                    listener.onAddFavorite(data) // Send to ViewModel for adding to favorites
                }
                saveLikedItems() // Save liked items to SharedPreferences
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

    private fun saveLikedItems() {
        // Convert likedItems to Set<String> for SharedPreferences
        prefs.edit().putStringSet("LIKED_ITEMS", likedItems.map { it.toString() }.toSet()).apply()
    }

    interface OnItemClickListener {
        fun onItemClick(position: PData)
        fun btnSaveClick(position: PData)
        fun onAddFavorite(position: Demo)
        fun onRemoveFavorite(position: Demo)
    }
}
