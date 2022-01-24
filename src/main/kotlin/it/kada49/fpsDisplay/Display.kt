package it.kada49.fpsDisplay

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class Display {

    @SubscribeEvent
    fun fpsDisplay(event: RenderGameOverlayEvent.Post) {

        val minecraft = Minecraft.getMinecraft()
        val fpsText = Minecraft.getDebugFPS().toString()

        if (minecraft.gameSettings.showDebugInfo) return
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return
        if (!Configuration.toggleSwitch) return

        val displayFPS = "${if (Configuration.prefixSwitch) "[FPS] " else ""}$fpsText${if (Configuration.suffixSwitch) " FPS" else ""}"

        minecraft.fontRendererObj.drawString(
            displayFPS,
            4F,
            4F,
            Configuration.fpsColor.rgb, Configuration.shadowSwitch
        )
    }
}


