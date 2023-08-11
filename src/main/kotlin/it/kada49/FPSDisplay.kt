package it.kada49

import gg.essential.universal.UMinecraft
import it.kada49.Constants.ID
import it.kada49.Constants.NAME
import it.kada49.Constants.VERSION
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


@Mod(modid = ID, name = NAME, version = VERSION, modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter", updateJSON = "https://kada49.github.io/json/FPS-Display-updateJson.json")

object FPSDisplay {

    var gui: GuiScreen? = null

    @EventHandler
    @Suppress("unused")
    fun init(event: FMLInitializationEvent) {
        ClientCommandHandler.instance.registerCommand(Command())

        Configuration.preload()

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(Overlay())
        MinecraftForge.EVENT_BUS.register(Update())
    }

    @SubscribeEvent
    @Suppress("unused")
    fun tick(event: TickEvent.ClientTickEvent) {
        if (gui != null) {
            try {
                UMinecraft.getMinecraft().displayGuiScreen(gui)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            gui = null
        }
    }
}