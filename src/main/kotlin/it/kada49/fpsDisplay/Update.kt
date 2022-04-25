package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Constants.IS_BETA
import it.kada49.fpsDisplay.Constants.PREFIX
import it.kada49.fpsDisplay.Constants.MC_VERSION
import it.kada49.fpsDisplay.Constants.VERSION
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

        val recommendedNumber = updateJson.get("promos").asJsonObject.get("${MC_VERSION}-recommended").toString()
            .replace('"'.toString(), "")
            .replace(".", "").toInt()
        val thisVersionNumber = VERSION.replace(".", "").toInt()
        val latest = updateJson.get("promos").asJsonObject.get("${MC_VERSION}-latest").toString()
            .replace('"'.toString(), "")
        val latestNumber = latest.replace(".", "").toInt()

        /**
         * Sends the correct message to the player according to the current version and the latest and recommended available version.
         */
        if (IS_BETA) { //Beta
            if (latestNumber < thisVersionNumber) { UChat.chat("$PREFIX §aThis version ($VERSION) is currently a BETA version!") }
            else { UChat.chat("$PREFIX §aThis version ($VERSION) is currently a BETA version, but a stable release ($latest) is available!") }
        }
        else { //Stable release
            if (latestNumber == thisVersionNumber) {
                if (upToDateNotification) UChat.chat("$PREFIX §aMod UP-TO-DATE! (${VERSION})")
            } else if (latestNumber >= thisVersionNumber) {
                if (recommendedNumber != latestNumber) { UChat.chat("$PREFIX §aVersion $latest available! (Not recommended!) §f /fps -> Links -> GitHub") }
                else { UChat.chat("$PREFIX §aVersion $latest available! §f /fps -> Links -> GitHub") }
            } else { UChat.chat("$PREFIX §aWhy are you in the future?!?") }
        }

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
                try { checkForModUpdates() } catch (e: Exception) { e.printStackTrace() }
            }.start()
            MinecraftForge.EVENT_BUS.unregister(this)
        }
    }

}