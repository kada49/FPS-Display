package com.kada49.fpsDisplay

import com.kada49.fpsDisplay.Constants.Colors
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class FpsDisplay {

    @SubscribeEvent
    fun fpsDisplay(event: RenderGameOverlayEvent.Pre) {

        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return
        if (!Configuration.toggleSwitch) return

        val minecraft = Minecraft.getMinecraft()
        var framesPerSecond = Minecraft.getDebugFPS()

        minecraft.fontRendererObj.drawString(
            framesPerSecond.toString() + " FPS",
            4F,
            4F,
            Colors.INTEGER[Configuration.colorSelector], Configuration.shadowSwitch
        )
    }
}