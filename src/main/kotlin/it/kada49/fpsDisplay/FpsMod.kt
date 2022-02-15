package it.kada49.fpsDisplay

import gg.essential.universal.UMinecraft
import gg.essential.universal.UScreen
import gg.essential.vigilance.Vigilance
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


@Mod(name = Constants.Data.NAME, modid = Constants.Data.ID, version = Constants.Data.VERSION, modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter")

object FpsMod{

    var vigilantGui: UScreen? = null

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent){

        Constants.Data.LOGGER.info("Initialisation started for ${event.side}")

        Vigilance.initialize()
        Configuration.preload()

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(Display())
        MinecraftForge.EVENT_BUS.register(Update())

        ClientCommandHandler.instance.registerCommand(Command())
    }

    @SubscribeEvent @Suppress("unused")
    fun tick(event: TickEvent.ClientTickEvent) {
        tick()
    }

    private fun tick() {
        if (vigilantGui != null) {
            try { UMinecraft.getMinecraft().displayGuiScreen(vigilantGui) } catch (e: Exception) { e.printStackTrace() }
            vigilantGui = null
        }
    }


}