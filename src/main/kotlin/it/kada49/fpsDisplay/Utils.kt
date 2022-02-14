package it.kada49.fpsDisplay

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gg.essential.universal.UChat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Desktop
import java.net.URI


object Utils {

    fun fetchJson(url: String): JsonObject {
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
        } catch (_: Exception) {}
    }

    fun position(Axis: String, Text: String, scale: Float): Float {

        val minecraft = Minecraft.getMinecraft()

        val textHeight = 8
        val textWidth = minecraft.fontRendererObj.getStringWidth(Text)
        var pixels = 0F
        if (Axis == "x") {
            when (Configuration.positionSelector) {
                0, 2 -> pixels = 4F
                1, 3 -> pixels = ( minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - (4 + (textWidth - 1) * scale ) )
            }
        }
        if (Axis == "y") {
            when (Configuration.positionSelector) {
                0, 1 -> pixels = 4F
                2, 3 -> pixels = ( minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - (4 + (textHeight - 1) * scale ) )
            }
        }
        return pixels
    }
}