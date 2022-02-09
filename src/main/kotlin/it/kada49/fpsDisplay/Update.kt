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

        val updateJson = Utils.fetchJson("https://api.jsonbin.io/b/61e6827c0f639830851e4cab/latest")

        val recommendedNumber = updateJson.get("promos").asJsonObject.get("1.8.9-recommended").toString()
            .replace('"'.toString(), "")
            .replace(".", "").toInt()
        val thisVersionNumber = Constants.Data.VERSION
            .replace(".", "").toInt()
        val latest = updateJson.get("promos").asJsonObject.get("1.8.9-latest").toString()
            .replace('"'.toString(), "")
        val latestNumber = latest.replace(".", "").toInt()

        var message = ""

        if (latestNumber == thisVersionNumber) {
            if (upToDateNotification) message = "Mod UP-TO-DATE! (${Constants.Data.VERSION})"
        } else if (latestNumber >= thisVersionNumber) {
            if (recommendedNumber != latestNumber) { message = "Version $latest available! (Not recommended!) §f /fps -> Links -> GitHub" }
            else { message = "Version $latest available! §f /fps -> Links -> GitHub" }
        } else { message = "Why are you in the future?!?" }

    if (message != "") { UChat.chat("$PREFIX §a$message") }
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