package it.kada49.fpsDisplay

import it.kada49.fpsDisplay.Axle.*
import it.kada49.fpsDisplay.Configuration.backgroundColor
import it.kada49.fpsDisplay.Configuration.backgroundSwitch
import it.kada49.fpsDisplay.Configuration.edgeSlider
import it.kada49.fpsDisplay.Configuration.fpsColor
import it.kada49.fpsDisplay.Configuration.positionSelector
import it.kada49.fpsDisplay.Configuration.scaleSlider
import it.kada49.fpsDisplay.Configuration.shadowSwitch
import it.kada49.fpsDisplay.Configuration.toggleSwitch
import it.kada49.fpsDisplay.Utils.boxPosition
import it.kada49.fpsDisplay.Utils.fpsText
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui.drawRect
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class RenderOverlay {

    private val minecraft: Minecraft = Minecraft.getMinecraft()

    @SubscribeEvent
    @Suppress("unused")
    fun fps(event: RenderGameOverlayEvent.Post) {

        /**
         * Conditions for not showing the overlay.
         */
        if (
            !toggleSwitch ||
            minecraft.gameSettings.showDebugInfo ||
            event.type != RenderGameOverlayEvent.ElementType.TEXT ||
            (positionSelector == 3 && minecraft.ingameGUI.chatGUI.chatOpen) ||
            (positionSelector == 4 && minecraft.ingameGUI.chatGUI.chatOpen)
        ) return

        /**
         * Sets the position.
         */
        val boxPosition = boxPosition(fpsText(), edgeSlider, scaleSlider)

        /**
         * Draws the background rectangle.
         */
        if (backgroundSwitch) {
            drawRect(boxPosition[0], boxPosition[1], boxPosition[2], boxPosition[3], backgroundColor.rgb)
        }

        /**
         * Draws the overlay text.
         */
        GlStateManager.pushMatrix()
        GlStateManager.translate(
            Utils.textPosition(X, fpsText(), scaleSlider).toDouble(),
            Utils.textPosition(Y, fpsText(), scaleSlider).toDouble(),
            0.0
        )
        GlStateManager.scale(scaleSlider.toDouble(), scaleSlider.toDouble(), scaleSlider.toDouble())
        minecraft.fontRendererObj.drawString(fpsText(), 0F, 0F, fpsColor.rgb, shadowSwitch)
        GlStateManager.popMatrix()

    }
}