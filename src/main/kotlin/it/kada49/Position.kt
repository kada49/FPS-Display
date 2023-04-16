package it.kada49

import it.kada49.Configuration.scaleSlider
import it.kada49.Position.Locations.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution

class Position(private var text: String, private var positionIndex: Int, private var edge: Int, private var scale: Int) {

    private val minecraft = Minecraft.getMinecraft()

    enum class Locations {
        TOP_LEFT,
        TOP_MIDDLE,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    fun getX(): Double {
        val textWidth = minecraft.fontRendererObj.getStringWidth(text)

        return when (Locations.values()[positionIndex]) {
            TOP_MIDDLE -> (ScaledResolution(minecraft).scaledWidth - (textWidth - 1) * scaleSlider) / 2.0
            TOP_LEFT, BOTTOM_LEFT -> 4.0
            TOP_RIGHT, BOTTOM_RIGHT -> ScaledResolution(minecraft).scaledWidth - (4 + (textWidth - 1.0) * scaleSlider)
        }
    }

    fun getY(): Double {
        return when (Locations.values()[positionIndex]) {
            TOP_LEFT, TOP_MIDDLE, TOP_RIGHT -> 4.0
            BOTTOM_LEFT, BOTTOM_RIGHT -> ScaledResolution(minecraft).scaledHeight - (4 + 7.0 * scaleSlider)
        }
    }

    fun getBox(): List<Int> {
        val textWidth = minecraft.fontRendererObj.getStringWidth(text) - 1

        val position = mutableListOf(0, 0, 0, 0)

        when (Locations.values()[positionIndex]) {

            TOP_LEFT -> {
                position[0] = 4 - edge
                position[1] = 4 - edge
                position[2] = 4 + edge + textWidth * scale
                position[3] = 4 + edge + 7 * scale
            }

            TOP_MIDDLE -> {
                position[0] = (-2 * edge - textWidth * scale + ScaledResolution(minecraft).scaledWidth) / 2
                position[1] = 4 - edge
                position[2] = (2 * edge + textWidth * scale + ScaledResolution(minecraft).scaledWidth) / 2
                position[3] = 4 + edge + 7 * scale
            }

            TOP_RIGHT -> {
                position[0] = -4 - edge - textWidth * scale + ScaledResolution(minecraft).scaledWidth
                position[1] = 4 - edge
                position[2] = -4 + edge + ScaledResolution(minecraft).scaledWidth
                position[3] = 4 + edge + 7 * scale
            }

            BOTTOM_LEFT -> {
                position[0] = 4 - edge
                position[1] = -4 - edge - 7 * scale + ScaledResolution(minecraft).scaledHeight
                position[2] = 4 + edge + textWidth * scale
                position[3] = -4 + edge + ScaledResolution(minecraft).scaledHeight
            }

            BOTTOM_RIGHT -> {
                position[0] = -4 - edge - textWidth * scale + ScaledResolution(minecraft).scaledWidth
                position[1] = -4 - edge - 7 * scale + ScaledResolution(minecraft).scaledHeight
                position[2] = -4 + edge + ScaledResolution(minecraft).scaledWidth
                position[3] = -4 + edge + ScaledResolution(minecraft).scaledHeight
            }
        }

        return position
    }
}