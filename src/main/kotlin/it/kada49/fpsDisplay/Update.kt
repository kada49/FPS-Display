package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import it.kada49.fpsDisplay.Constants.IS_BETA
import it.kada49.fpsDisplay.Constants.PREFIX
import it.kada49.fpsDisplay.Utils.fetchJson
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent


class Update(private val version: String) {

    private val latest: String

    private val minecraft = Minecraft.getMinecraft()
    private var hasTriggered = false

    init {
        val updateJson = fetchJson("https://api.github.com/repos/kada49/FPS-Display/releases/latest")
        this.latest = updateJson["tag_name"].asString
    }

    fun checkForModUpdates(upToDateNotification: Boolean = false) {

        //Fetches the JSON object containing information about the newest versions.

        val latestNumber = this.latest.replace(".", "").toInt()
        val thisVersionNumber = this.version.replace(".", "").toInt()


        //Sends the correct message to the player according to the current version and the latest and recommended available version.

        //Beta
        if (IS_BETA) {
            if (latestNumber < thisVersionNumber) {
                UChat.chat("$PREFIX §fThis version (§e$version§f) is currently a BETA version!")
                return
            }

            UChat.chat("$PREFIX §fYou are on a BETA version, a stable release (§e$latest§f) is available!")
            return
        }

        //Stable release
        if (latestNumber == thisVersionNumber && upToDateNotification) {
            UChat.chat("$PREFIX §fMod UP-TO-DATE! (§e$version§f)")
            return
        }
        if (latestNumber > thisVersionNumber) {
            UChat.chat("$PREFIX §fVersion §e$latest §favailable! /fps -> Links -> GitHub")
            return
        }
        UChat.chat("$PREFIX §fWhy are you in the future?!?")
    }

    // Copied and adapted from MegaWallsEnhancements Mod: https://github.com/Alexdoru/MegaWallsEnhancements
    /**
     * Disables the notification after the first time the player joined a world/server since the player restarted the game.
     */
    @SubscribeEvent
    @Suppress("unused")
    fun onTick(event: ClientTickEvent) {
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