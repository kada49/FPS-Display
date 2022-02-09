package it.kada49.fpsDisplay

import it.kada49.fpsDisplay.Configuration.scaleSlider
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class Display {

    private val minecraft: Minecraft = Minecraft.getMinecraft()

    @SubscribeEvent
    fun fps(event: RenderGameOverlayEvent.Post) {

        if (
            !Configuration.toggleSwitch ||
            minecraft.gameSettings.showDebugInfo ||
            event.type != RenderGameOverlayEvent.ElementType.TEXT ||
            ( Configuration.positionSelector == 2 && minecraft.ingameGUI.chatGUI.chatOpen ) ||
            ( Configuration.positionSelector == 3 && minecraft.ingameGUI.chatGUI.chatOpen )
        ) return

        val fpsText = Minecraft.getDebugFPS().toString()
        val displayFPS = "${if (Configuration.prefixSwitch) "[FPS] " else ""}$fpsText${if (Configuration.suffixSwitch) " FPS" else ""}"

        GlStateManager.pushMatrix()
            GlStateManager.translate(position("x", displayFPS, scaleSlider.toFloat()).toDouble(), position("y", displayFPS, scaleSlider.toFloat()).toDouble(), 0.0)
            GlStateManager.scale(scaleSlider.toDouble(), scaleSlider.toDouble(), scaleSlider.toDouble())
            minecraft.fontRendererObj.drawString(
                displayFPS,
                0F,
                0F,
                Configuration.fpsColor.rgb, Configuration.shadowSwitch
            )
        GlStateManager.popMatrix()

    }

    private fun position(Axis: String, Text: String, scale: Float): Float {

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