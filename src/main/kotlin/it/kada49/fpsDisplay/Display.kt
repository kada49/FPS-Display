package it.kada49.fpsDisplay

import it.kada49.fpsDisplay.Configuration.edgeSlider
import it.kada49.fpsDisplay.Configuration.scaleSlider
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui.drawRect
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color


class Display {

    private val minecraft: Minecraft = Minecraft.getMinecraft()

    @SubscribeEvent @Suppress("unused")
    fun fps(event: RenderGameOverlayEvent.Post) {

        /**
         * Conditions for not showing the overlay.
         */
        if (
            !Configuration.toggleSwitch ||
            minecraft.gameSettings.showDebugInfo ||
            event.type != RenderGameOverlayEvent.ElementType.TEXT ||
            ( Configuration.positionSelector == 2 && minecraft.ingameGUI.chatGUI.chatOpen ) ||
            ( Configuration.positionSelector == 3 && minecraft.ingameGUI.chatGUI.chatOpen )
        ) return

        /**
         * Get the frames number and creates the text to be shown.
         */
        val fpsText = Minecraft.getDebugFPS().toString()
        val displayFPS = "${if (Configuration.prefixSwitch) "[FPS] " else ""}$fpsText${if (Configuration.suffixSwitch) " FPS" else ""}"

        /**
         * Sets the background colour and position.
         */
        val backgroundColor = Color(Configuration.backgroundColor.red, Configuration.backgroundColor.green, Configuration.backgroundColor.blue, Configuration.alphaSlider).rgb
        val boxPosition = Utils.boxPosition(displayFPS, edgeSlider, scaleSlider)

        /**
         * Draws the background rectangle.
         */
        if (Configuration.backgroundSwitch) drawRect(boxPosition[0], boxPosition[1], boxPosition[2], boxPosition[3], backgroundColor)

        /**
         * Draws the overlay text.
         */
        GlStateManager.pushMatrix()
        GlStateManager.translate(Utils.textPosition("x", displayFPS, scaleSlider).toDouble(), Utils.textPosition("y", displayFPS, scaleSlider).toDouble(), 0.0)
        GlStateManager.scale(scaleSlider.toDouble(), scaleSlider.toDouble(), scaleSlider.toDouble())
        minecraft.fontRendererObj.drawString( displayFPS, 0F, 0F, Configuration.fpsColor.rgb, Configuration.shadowSwitch )
        GlStateManager.popMatrix()

    }
}