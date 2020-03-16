package com.outcomehealth.data

import android.content.Context
import com.google.gson.Gson
import com.outcomehealth.lib.VideoOH
import java.io.IOException
import java.io.InputStream
import java.io.StringReader
import java.nio.charset.Charset

class VideoRepository(private val androidContext: Context) {

    companion object {
        const val JSON_ASSET = "code-challenge-manifest.json"
        const val CHARSET_UTF8 = "UTF-8"
    }

    private val videos = mutableListOf<VideoOH>()


    fun loadVideoGallery(): List<VideoOH> {
        videos.clear()

        val jsonStr = loadJSONFromAsset()
        jsonStr?.let { videos.addAll(serializeJsonArray(it)) }

        return videos
    }

    fun loadVideoById(title: String): VideoOH? {
        return videos.find { it.title == title }
    }

    private fun loadJSONFromAsset(): String? = try {
        val inputStream: InputStream = androidContext.assets.open(JSON_ASSET)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName(CHARSET_UTF8))

    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }

    private fun serializeJsonArray(jsonStr: String): List<VideoOH> {
        val stringReader = StringReader(jsonStr)
        return Gson().fromJson(stringReader, Array<VideoOH>::class.java).toList()
    }
}