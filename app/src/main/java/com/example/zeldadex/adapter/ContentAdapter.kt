package com.example.zeldadex.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.R
import com.example.zeldadex.model.Content
import com.example.zeldadex.util.BitmapTask

class ContentAdapter(private val dataSet: List<Content>) : RecyclerView.Adapter<ContentAdapter.ContentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_horizontal, parent, false)
        return ContentHolder(view)
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val current = dataSet[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ContentHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView = itemView.findViewById<ImageView>(R.id.img_vertical_list)
        fun bind(current: Content) {
            BitmapTask(object : BitmapTask.Callback{
                override fun onResult(bitmap: Bitmap) {
                    imgView.setImageBitmap(bitmap)
                }

            }).exec(current.image)
        }
    }
}