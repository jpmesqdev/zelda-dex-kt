package com.example.zeldadex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeldadex.adapter.CategoryAdapter
import com.example.zeldadex.model.Category
import com.example.zeldadex.model.Content

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf<Content>(
            Content("a"),
            Content("a"),
            Content("a"),
            Content("a"),
            Content("a"),
        )

        adapter = CategoryAdapter(mutableListOf(
            Category("Creatures", list),
            Category("Equipment", list),
            Category("Materials", list),
            Category("Monsters", list),
            Category("Treasure", list),
        ))
        rv = findViewById(R.id.rv_act_main_category)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}