package com.example.zeldadex.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.R
import com.example.zeldadex.model.Category
import com.example.zeldadex.util.BitmapTask

class CategoryAdapter(private val context: Context,
                      private val dataSet: List<Category>,
                      private val onItemClickListener: ((Int) -> Unit)
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

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
            val rv = itemView.findViewById<RecyclerView>(R.id.rv_horizontal)

            txtCat.text = current.name
            rv.adapter = ContentAdapter(context, current.contentList, onItemClickListener)
            rv.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        }
    }
}