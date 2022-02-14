package it.kada49.fpsDisplay

import it.kada49.fpsDisplay.Configuration.scaleSlider
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui.drawRect
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color


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

        val hoverColor = Color(Configuration.backgroundColor.red, Configuration.backgroundColor.green, Configuration.backgroundColor.blue, Configuration.alphaSlider).rgb

        GlStateManager.pushMatrix()
            GlStateManager.scale(scaleSlider.toDouble(), scaleSlider.toDouble(), scaleSlider.toDouble())
            if (Configuration.backgroundSwitch) drawRect((4 - Configuration.edgeSlider) / scaleSlider, (4 - Configuration.edgeSlider) / scaleSlider, minecraft.fontRendererObj.getStringWidth(displayFPS) -1 + (2 + 2 * Configuration.edgeSlider) / scaleSlider, 7 + (2 + 2 * Configuration.edgeSlider) / scaleSlider, hoverColor)
        GlStateManager.popMatrix()

        GlStateManager.pushMatrix()
            GlStateManager.translate(Utils.position("x", displayFPS, scaleSlider.toFloat()).toDouble(), Utils.position("y", displayFPS, scaleSlider.toFloat()).toDouble(), 0.0)
            GlStateManager.scale(scaleSlider.toDouble(), scaleSlider.toDouble(), scaleSlider.toDouble())
            minecraft.fontRendererObj.drawString(
                displayFPS,
                0F,
                0F,
                Configuration.fpsColor.rgb, Configuration.shadowSwitch
            )
        GlStateManager.popMatrix()

    }
}