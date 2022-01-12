package com.kada49.fpsDisplay

import gg.essential.universal.UMinecraft
import gg.essential.universal.UScreen
import gg.essential.vigilance.Vigilance
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


@Mod(name = Constants.Data.NAME, modid = Constants.Data.ID, version = Constants.Data.VERSION, modLanguageAdapter = "KotlinLanguageAdapter")

object MainMod{

    @Mod.EventHandler
    fun preinit(event: FMLPreInitializationEvent){

        Constants.Data.LOGGER.info("Preinitialisation started for ${event.side}")

        Vigilance.initialize()
        Configuration.preload()

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(FpsDisplay())

        ClientCommandHandler.instance.registerCommand(Command())
    }

    @SubscribeEvent
    fun tick(event: TickEvent.ClientTickEvent) {
        tick()
    }

    private fun tick() {
        if (gui != null) {
            try {
                UMinecraft.getMinecraft().displayGuiScreen(gui)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            gui = null
        }
    }

    var gui: UScreen? = null

}