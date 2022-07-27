package it.kada49.fpsDisplay

import gg.essential.universal.UMinecraft
import gg.essential.universal.UScreen
import gg.essential.vigilance.Vigilance
import it.kada49.fpsDisplay.Constants.LOGGER
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


@Mod(
    name = Constants.NAME,
    modid = Constants.ID,
    version = Constants.VERSION,
    modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter",
    updateJSON = "https://api.jsonstorage.net/v1/json/43187124-5250-4d1f-b4e7-65ea8e411651/8ab2e620-19a5-4f31-931e-7c9fc359fcf6",
    guiFactory = "it.kada49.fpsDisplay.FpsDisplay.l"
)

object FpsDisplayMod{

    var vigilantGui: UScreen? = null

    @EventHandler @Suppress("unused")
    fun init(event: FMLInitializationEvent){

        LOGGER.info("Initialization started for ${event.side}")

        Vigilance.initialize()
        Configuration.preload()

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(RenderOverlay())
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