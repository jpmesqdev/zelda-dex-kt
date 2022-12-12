package com.example.zeldadex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.adapter.CategoryAdapter
import com.example.zeldadex.model.Category
import com.example.zeldadex.model.Content
import com.example.zeldadex.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {

    private lateinit var rv: RecyclerView
    private val categories = mutableListOf<Category>()
    private val adapter: CategoryAdapter = CategoryAdapter(categories)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv_act_main_category)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        CategoryTask(this).exec("https://botw-compendium.herokuapp.com/api/v2/all")
    }

    override fun onResult(categories: List<Category>) {
        Log.i("mamas", categories.toString())
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {

    }

    override fun onPreExecute() {

    }
}