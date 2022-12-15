package com.example.zeldadex.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.zeldadex.model.Category
import com.example.zeldadex.model.Content
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val callback: Callback) {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val executor: Executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
        fun onPreExecute()
    }

    fun exec(url: String) {
        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {

                val requestUrl: URL = URL(url)
                urlConnection = requestUrl.openConnection() as HttpsURLConnection?

                urlConnection?.connectTimeout = 2000
                urlConnection?.readTimeout = 2000

                val responseCode = urlConnection?.responseCode

                if (responseCode != null) {
                    if (responseCode >= 400) {
                        throw IOException("Server Error")
                    }
                }

                stream = urlConnection?.inputStream
                val streamAsJson = stream?.bufferedReader().use {
                    it?.readText()
                }

                val result = toCategories(streamAsJson)

                handler.post {
                    callback.onResult(result)
                }


            } catch (e: Exception) {
                val message = e.message ?: "Unknown Error"
                Log.e("Error", message)
                handler.post {
                    callback.onFailure(message)
                }
            } finally {
                stream?.close()
                urlConnection?.disconnect()
            }
        }
    }

    private fun toCategories(jsonString: String?): List<Category> {

        val categories = mutableListOf<Category>()

        val jsonRoot = jsonString?.let { JSONObject(it) }
        val data = jsonRoot?.getJSONObject("data")

        data!!.keys().forEach {
            var content: JSONArray
            val contentList = mutableListOf<Content>()
            if (it == "creatures") {
                content = data.getJSONObject(it).getJSONArray("non_food")
            } else {
                content = data.getJSONArray(it)
            }

            for (i in 0 until content.length()) {
                val jsonContent = content.getJSONObject(i)
                val category = jsonContent.getString("category")
//                val commonLoc = jsonContent.getJSONArray("common_locations")
                val description = jsonContent.getString("description")
                //val drops = jsonContent.getJSONArray("drops")
                val id = jsonContent.getInt("id")
                val image = jsonContent.getString("image")
                val name = jsonContent.getString("name")

                val commonLocList = mutableListOf<String>()
                val dropsList = mutableListOf<String>()

//                for (j in 0 until commonLoc.length()) {
//                    commonLocList.add(commonLoc[j] as String)
//                    if (commonLoc.length() > 1) {
//                        commonLocList.add(commonLoc[j + 1] as String)
//                    }
//                }

//                for (j in 0 until drops.length()) {
//                    dropsList.add(drops[j] as String)
//                    if (drops.length() > 1) {
//                        dropsList.add(drops[j + 1] as String)
//                    }
//                }

                contentList.add(Content(category, commonLocList, description, dropsList, id, image, name))
            }

           categories.add(
               Category(it, contentList)
           )
        }


        return categories

    }
}