package com.example.furniq.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.furniq.R
import com.example.furniq.data.CategoriesData.CData
import com.example.furniq.data.get_all_products_data.PData

class CategoriesAdapter(private val listener : OnItemClickListener): RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    var models: List<CData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val textCategories : TextView = itemView.findViewById(R.id.text_kategoriyalar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categories, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            textCategories.text = models[position].name.latin

            itemView.setOnClickListener {

                listener.onItemClick(models[position])

            }

        }
    }

    override fun getItemCount(): Int = models.size


    interface OnItemClickListener {
        fun onItemClick(position: CData)
    }

}