package it.kada49.fpsDisplay

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class FpsDisplay {

    @SubscribeEvent
    fun fpsDisplay(event: RenderGameOverlayEvent.Post) {

        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return
        if (!Configuration.toggleSwitch) return

        val minecraft = Minecraft.getMinecraft()

        var displayFPS = "${if (Configuration.prefixSwitch) "[FPS] " else ""}${Minecraft.getDebugFPS()}${if (Configuration.suffixSwitch) " FPS" else ""}"

        minecraft.fontRendererObj.drawString(
            displayFPS,
            4F,
            4F,
            Configuration.fpsColor.rgb, Configuration.shadowSwitch
        )
    }
}


