package com.example.zeldadex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.R
import com.example.zeldadex.model.Category

class CategoryAdapter(private val dataSet: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_vertical, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val current = dataSet[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(current: Category) {
            val txtCat = itemView.findViewById<TextView>(R.id.txt_vertical_list)
            txtCat.text = current.name
        }
    }
}