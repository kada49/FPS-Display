package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Constants.Data.PREFIX
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent


class CheckForUpdates {

    private val minecraft = Minecraft.getMinecraft()
    private var hasTriggered = false

    private fun checkForModUpdates() {
        val version = Constants.Data.VERSION.replace(".", "").toInt()
        val updateJson = Utils.fetchHypixelApi("https://api.jsonbin.io/b/61e6827c0f639830851e4cab/latest").get("promos").asJsonObject.get("1.8.9-latest").toString().replace('"'.toString(), "").replace(".", "").toInt()

        var message = ""

        if (updateJson == version) return
        else if (updateJson >= version) { message = "Version $updateJson avaiable! | /fps -> Links -> GitHub" }
        else { message = "Why are you in the future?!?" }
        message = "$PREFIX §a$message"
        UChat.chat(message)
    }


    // Copied from MegaWallsEnhancements Mod: https://github.com/Alexdoru/MegaWallsEnhancements
    @SubscribeEvent
    fun onTick(event: ClientTickEvent?) {
        if (minecraft.theWorld != null && minecraft.thePlayer != null && !hasTriggered) {
            hasTriggered = true
            Thread {
                try {
                    checkForModUpdates()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
            MinecraftForge.EVENT_BUS.unregister(this)
        }
    }

}