package com.kada49.fpsDisplay

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class FpsDisplay {

    @SubscribeEvent
    fun fpsDisplay(event: RenderGameOverlayEvent) {

        val minecraft = Minecraft.getMinecraft()
        var framesPerSecond = Minecraft.getDebugFPS()

        minecraft.fontRendererObj.drawString(framesPerSecond.toString() + " FPS", 5F, 5F, 0xFFFFFFFF.toInt(), true)
    }
}