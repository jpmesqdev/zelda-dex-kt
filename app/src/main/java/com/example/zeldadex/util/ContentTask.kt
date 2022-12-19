package com.example.zeldadex.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.zeldadex.model.Category
import com.example.zeldadex.model.Content
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class ContentTask(private val callback: Callback) {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val executor: Executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(content: Content)
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

                val result = toContent(streamAsJson)

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

    private fun toContent(jsonAsString: String?): Content {
        val jsonRoot = jsonAsString?.let { JSONObject(it) }
        val data = jsonRoot!!.getJSONObject("data")
        val category = data.getString("category")
        val description = data.getString("description")
        val id = data.getInt("id")
        val image = data.getString("image")
        val name = data.getString("name")

        return Content(category, mutableListOf(), description, mutableListOf(), id, image, name)

    }
}