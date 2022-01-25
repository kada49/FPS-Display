package it.kada49.fpsDisplay

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class Display {

    private val minecraft: Minecraft = Minecraft.getMinecraft()

    @SubscribeEvent
    fun fps(event: RenderGameOverlayEvent.Post) {

        val fpsText = Minecraft.getDebugFPS().toString()

        if (minecraft.gameSettings.showDebugInfo) return
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return
        if (!Configuration.toggleSwitch) return
        if (Configuration.positionSelector == 2 && minecraft.ingameGUI.chatGUI.chatOpen) return
        if (Configuration.positionSelector == 3 && minecraft.ingameGUI.chatGUI.chatOpen) return

        val displayFPS = "${if (Configuration.prefixSwitch) "[FPS] " else ""}$fpsText${if (Configuration.suffixSwitch) " FPS" else ""}"

        minecraft.fontRendererObj.drawString(
            displayFPS,
            position("x", displayFPS),
            position("y", displayFPS),
            Configuration.fpsColor.rgb, Configuration.shadowSwitch
        )

    }

    private fun position(Axis: String, Text: String): Float {
        var pixels = 0F
        if (Axis == "x") {
            when (Configuration.positionSelector) {
                0, 2 -> pixels = 4F
                1, 3 -> pixels = ( minecraft.displayWidth / ScaledResolution(minecraft).scaleFactor - ( minecraft.fontRendererObj.getStringWidth(Text) + 3 ) ).toFloat()
            }
        }
        if (Axis == "y") {
            when (Configuration.positionSelector) {
                0, 1 -> pixels = 4F
                2, 3 -> pixels = ( minecraft.displayHeight / ScaledResolution(minecraft).scaleFactor - 11 ).toFloat()
            }
        }
        return pixels
    }
}