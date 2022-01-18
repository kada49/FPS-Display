package it.kada49.fpsDisplay

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gg.essential.universal.UChat
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Desktop
import java.net.URI


object Utils {

    fun fetchHypixelApi(url: String): JsonObject {
        val gson = JsonParser()
        var result: String? = null

        val client = OkHttpClient()
        try {

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            result = response.body?.string()
        } catch (error: Error) { UChat.chat("An error occured requesting from '" + url + "': " + error.localizedMessage) }

        return gson.parse(result) as JsonObject
    }

    fun openUrl(url: String) {
        try {
            Desktop.getDesktop().browse( URI(url) )
        } catch (exception: Exception) {}
    }
}