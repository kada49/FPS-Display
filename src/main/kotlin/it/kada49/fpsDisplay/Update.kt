package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Constants.IS_BETA
import it.kada49.fpsDisplay.Constants.MC_VERSION
import it.kada49.fpsDisplay.Constants.PREFIX
import it.kada49.fpsDisplay.Constants.VERSION
import it.kada49.fpsDisplay.Utils.fetchJson
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
        val updateJson = fetchJson("https://kada49.github.io/json/FPS-Display-updateJson.json")

        val recommendedNumber = updateJson["promos"].asJsonObject["${MC_VERSION}-recommended"].asString
            .replace('"'.toString(), "")
            .replace(".", "").toInt()
        val thisVersionNumber = VERSION.replace(".", "").toInt()
        val latest = updateJson["promos"].asJsonObject["${MC_VERSION}-latest"].asString
            .replace('"'.toString(), "")
        val latestNumber = latest.replace(".", "").toInt()

        /**
         * Sends the correct message to the player according to the current version and the latest and recommended available version.
         */

        //Beta
        if (IS_BETA) {
            if (latestNumber < thisVersionNumber) {
                UChat.chat("$PREFIX §aThis version ($VERSION) is currently a BETA version!")
                return
            }
            UChat.chat("$PREFIX §aThis version ($VERSION) is currently a BETA version, but a stable release ($latest) is available!")
            return
        }

        //Stable release
        if (latestNumber == thisVersionNumber) {
            if (upToDateNotification) UChat.chat("$PREFIX §aMod UP-TO-DATE! (${VERSION})")
            return
        } else if (latestNumber >= thisVersionNumber) {
            if (recommendedNumber != latestNumber) {
                UChat.chat("$PREFIX §aVersion $latest available! (Not recommended!) §f /fps -> Links -> GitHub")
                return
            }
            UChat.chat("$PREFIX §aVersion $latest available! §f /fps -> Links -> GitHub")
            return
        }
        UChat.chat("$PREFIX §aWhy are you in the future?!?")
    }

    // Copied and adapted from MegaWallsEnhancements Mod: https://github.com/Alexdoru/MegaWallsEnhancements
    /**
     * Disables the notification after the first time the player joined a world/server since the player restarted the game.
     */
    @SubscribeEvent
    @Suppress("unused")
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