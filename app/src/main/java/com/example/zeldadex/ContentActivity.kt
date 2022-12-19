package com.example.zeldadex

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.zeldadex.model.Content
import com.example.zeldadex.util.BitmapTask
import com.example.zeldadex.util.ContentTask

class ContentActivity : AppCompatActivity(), ContentTask.Callback {

    lateinit var progressBar: ProgressBar
    lateinit var imgView: ImageView
    lateinit var txtName: TextView
    lateinit var txtCategory: TextView
    lateinit var txtDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        progressBar = findViewById(R.id.content_progress)
        imgView = findViewById(R.id.img_content)
        txtName = findViewById(R.id.txt_content_name)
        txtCategory = findViewById(R.id.txt_content_cat)
        txtDesc = findViewById(R.id.txt_content_desc)

        onPreExecute()

        val id = intent.getStringExtra("id")

        if (id != null) {
            Toast.makeText(this, "id = $id", Toast.LENGTH_LONG).show()
        }

        ContentTask(this).exec("https://botw-compendium.herokuapp.com/api/v2/entry/$id")

    }

    override fun onResult(content: Content) {

        BitmapTask(object : BitmapTask.Callback{
            override fun onResult(bitmap: Bitmap) {
                imgView.setImageBitmap(bitmap)
            }
        }).exec(content.image)

        txtName.text = content.name.replaceFirstChar { it.uppercaseChar() }
        txtCategory.text = content.category.replaceFirstChar { it.uppercaseChar() }
        txtDesc.text = content.description.replaceFirstChar { it.uppercaseChar() }
        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progressBar.visibility = View.GONE
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }
}