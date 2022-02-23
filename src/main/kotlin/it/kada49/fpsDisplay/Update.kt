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

        /**
         * Fetches the JSON object containing information about the newest versions.
         */
        val updateJson = Utils.fetchJson("https://api.jsonstorage.net/v1/json/43187124-5250-4d1f-b4e7-65ea8e411651/8ab2e620-19a5-4f31-931e-7c9fc359fcf6")

        val recommendedNumber = updateJson.get("promos").asJsonObject.get("1.8.9-recommended").toString()
            .replace('"'.toString(), "")
            .replace(".", "").toInt()
        val thisVersionNumber = Constants.Data.VERSION
            .replace(".", "").toInt()
        val latest = updateJson.get("promos").asJsonObject.get("1.8.9-latest").toString()
            .replace('"'.toString(), "")
        val latestNumber = latest.replace(".", "").toInt()

        /**
         * Sends the correct message to the player according to the current version and the latest and recommended available version.
         */
        if (latestNumber == thisVersionNumber) {
            if (upToDateNotification) UChat.chat("$PREFIX §aMod UP-TO-DATE! (${Constants.Data.VERSION})")
        } else if (latestNumber >= thisVersionNumber) {
            if (recommendedNumber != latestNumber) { UChat.chat("$PREFIX §aVersion $latest available! (Not recommended!) §f /fps -> Links -> GitHub") }
            else { UChat.chat("$PREFIX §aVersion $latest available! §f /fps -> Links -> GitHub") }
        } else { UChat.chat("$PREFIX §aWhy are you in the future?!?") }

    }

    // Copied from MegaWallsEnhancements Mod: https://github.com/Alexdoru/MegaWallsEnhancements
    /**
     * Disables the notification after the first time the player joined a world/server since the player restarted the game.
     */
    @SubscribeEvent @Suppress("unused")
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