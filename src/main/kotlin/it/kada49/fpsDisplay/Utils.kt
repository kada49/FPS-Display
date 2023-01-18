package it.kada49.fpsDisplay

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Axle.*
import it.kada49.fpsDisplay.Configuration.bracketsSelector
import it.kada49.fpsDisplay.Configuration.prefixSwitch
import it.kada49.fpsDisplay.Configuration.suffixSwitch
import it.kada49.fpsDisplay.Position.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import java.awt.Desktop
import java.net.URI
import java.net.URL


object Utils {

    fun fetchJson(url: String): JsonObject {
        val gson = JsonParser()
        var result: String? = null

        try {
            result = URL(url).readText()
        } catch (error: Error) {
            UChat.chat("An error occurred requesting from '" + url + "': " + error.localizedMessage)
        }

        return gson.parse(result) as JsonObject
    }

    /**
     * Opens the given URL in the predefined browser.
     */
    fun openUrl(url: String) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (_: Exception) {
        }
    }

    /**
     * The FPS Overlay Text
     */
    fun fpsText(): String {
        val text: String

        val fps = Minecraft.getDebugFPS().toString()
        val prefix = prefixSwitch
        val suffix = suffixSwitch
        val brackets = bracketsSelector
        if (brackets) {
            text = if (prefix && !suffix) {
                "[FPS $fps]"
            } else if (suffix && !prefix) {
                "[$fps FPS]"
            } else if (!prefix) {
                "[$fps]"
            } else {
                "[FPS $fps fps]"
            }
        } else {
            text = if (prefix && !suffix) {
                "[FPS] $fps"
            } else if (suffix && !prefix) {
                "$fps FPS"
            } else if (!prefix) {
                fps
            } else {
                "[FPS] $fps fps"
            }
        }

        return text
    }

    fun textPosition(axle: Axle, text: String, scale: Int): Float {

        val minecraft = Minecraft.getMinecraft()

        val textHeight = 8
        val textWidth = minecraft.fontRendererObj.getStringWidth(text)

        val pixels = when (axle) {
            X -> {
                when (Position.values()[Configuration.positionSelector]) {
                    TOP_MIDDLE -> (minecraft.displayWidth / (2 * ScaledResolution(minecraft).scaleFactor) - ((textWidth - 1) * scale) / 2).toFloat()

                    TOP_LEFT, BOTTOM_LEFT -> 4F
                    TOP_RIGHT, BOTTOM_RIGHT -> (minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - (4 + (textWidth - 1) * scale)).toFloat()
                }
            }

            Y -> {
                when (Position.values()[Configuration.positionSelector]) {
                    TOP_LEFT, TOP_MIDDLE, TOP_RIGHT -> 4F
                    BOTTOM_LEFT, BOTTOM_RIGHT -> (minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - (4 + (textHeight - 1) * scale)).toFloat()
                }
            }
        }

        return pixels
    }

    fun boxPosition(text: String, edge: Int, scale: Int): Array<Int> {

        val minecraft = Minecraft.getMinecraft()
        val textHeight = 7
        val textWidth = minecraft.fontRendererObj.getStringWidth(text) - 1

        var left = 0
        var right = 0
        var top = 0
        var bottom = 0

        when (Position.values()[Configuration.positionSelector]) {
            TOP_LEFT -> {
                left = 4 - edge
                right = 4 + textWidth * scale + edge
                top = 4 - edge
                bottom = 4 + textHeight * scale + edge
            }

            TOP_MIDDLE -> {
                left =
                    (minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - 2 * edge - textWidth * scale) / 2
                right =
                    (minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor + 2 * edge + textWidth * scale) / 2
                top = 4 - edge
                bottom = 4 + textHeight * scale + edge
            }

            TOP_RIGHT -> {
                left = minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - 4 - edge - textWidth * scale
                right = minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - 4 + edge
                top = 4 - edge
                bottom = 4 + textHeight * scale + edge
            }

            BOTTOM_LEFT -> {
                left = 4 - edge
                right = 4 + textWidth * scale + edge
                top = minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - 4 - edge - textHeight * scale
                bottom = minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - 4 + edge
            }

            BOTTOM_RIGHT -> {
                left = minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - 4 - edge - textWidth * scale
                right = minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - 4 + edge
                top = minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - 4 - edge - textHeight * scale
                bottom = minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - 4 + edge
            }
        }
        return arrayOf(left, top, right, bottom)
    }
}