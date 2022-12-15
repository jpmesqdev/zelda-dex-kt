package com.example.zeldadex.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

class BitmapTask(val callback: Callback) {
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val executor: Executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(bitmap: Bitmap)
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

                val bitmap = BitmapFactory.decodeStream(stream)

                handler.post {
                    callback.onResult(bitmap)
                }

            } catch (e: Exception) {
                val message = e.message ?: "Unknown Error"
                Log.e("Error", message)
            } finally {
                stream?.close()
                urlConnection?.disconnect()
            }
        }
    }

}