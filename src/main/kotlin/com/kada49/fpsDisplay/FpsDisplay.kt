package com.kada49.fpsDisplay

import com.kada49.fpsDisplay.Constants.ColorsInt
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class FpsDisplay {

    @SubscribeEvent
    fun fpsDisplay(event: RenderGameOverlayEvent) {

        val minecraft = Minecraft.getMinecraft()
        var framesPerSecond = Minecraft.getDebugFPS()

        if (!Configuration.toggleSwitch) return
        else {
            minecraft.fontRendererObj.drawString(
                framesPerSecond.toString() + " FPS",
                4F,
                4F,
                ColorsInt.colors[Configuration.colorSelector], Configuration.shadowSwitch
            )
        }
    }
}