package it.kada49

import it.kada49.Configuration.backgroundColor
import it.kada49.Configuration.backgroundSwitch
import it.kada49.Configuration.bracketsSelector
import it.kada49.Configuration.edgeSlider
import it.kada49.Configuration.format
import it.kada49.Configuration.fpsColor
import it.kada49.Configuration.positionSelector
import it.kada49.Configuration.scaleSlider
import it.kada49.Configuration.shadowSwitch
import it.kada49.Configuration.toggleSwitch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui.drawRect
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class Overlay {

    private val minecraft = Minecraft.getMinecraft()

    @SubscribeEvent
    @Suppress("unused")
    fun render(event: RenderGameOverlayEvent.Post) {
        val scaleSliderFloat = scaleSlider.toFloat()

        val text = when (format) {
            0 -> if (bracketsSelector) "[FPS] " else "FPS " + Minecraft.getDebugFPS().toString()
            1 -> Minecraft.getDebugFPS().toString() + if (bracketsSelector) " [FPS]" else " FPS"
            else -> Minecraft.getDebugFPS().toString()
        }

        val pos = Position(text, positionSelector, edgeSlider, scaleSlider)

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
         * Draws the background rectangle.
         */
        if (backgroundSwitch) {
            val box = pos.getBox()
            drawRect(box[0], box[1], box[2], box[3], backgroundColor.rgb)
        }



        GlStateManager.pushMatrix()
        GlStateManager.translate(pos.getX(), pos.getY(), 0.0)
        GlStateManager.scale(scaleSliderFloat, scaleSliderFloat, scaleSliderFloat)

        minecraft.fontRendererObj.drawString(text, 0F, 0F, fpsColor.rgb, shadowSwitch)

        GlStateManager.popMatrix()

    }
}