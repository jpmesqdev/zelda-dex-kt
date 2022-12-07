package com.example.zeldadex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.R
import com.example.zeldadex.model.Content

class ContentAdapter(val dataSet: List<Content>) : RecyclerView.Adapter<ContentAdapter.ContentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_horizontal, parent, false)
        return ContentHolder(view)
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ContentHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}