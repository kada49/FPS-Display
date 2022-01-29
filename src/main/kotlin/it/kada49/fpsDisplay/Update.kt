package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Constants.Data.PREFIX
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent


class Update {

    private val minecraft = Minecraft.getMinecraft()
    private var hasTriggered = false

    fun checkForModUpdates(upToDateNotification: Boolean = false) {

        val json = Utils.fetchHypixelApi("https://api.jsonbin.io/b/61e6827c0f639830851e4cab/latest")
        val recommended = json.get("promos").asJsonObject.get("1.8.9-recommended").toString().replace('"'.toString(), "").replace(".", "").toInt()
        val version = Constants.Data.VERSION.replace(".", "").toInt()
        val updateJson = json.get("promos").asJsonObject.get("1.8.9-latest").toString().replace('"'.toString(), "")
        val newVersion = updateJson.replace(".", "").toInt()

        var message = if (newVersion == version) {
            if (upToDateNotification) "Mod UP-TO-DATE! (${Constants.Data.VERSION})" else return
        } else if (newVersion >= version) "Version $updateJson available!${if (recommended != newVersion) "(Not recommended!)" else ""}§f /fps -> Links -> GitHub"
        else "Why are you in the future?!?"

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