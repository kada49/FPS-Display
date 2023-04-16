package it.kada49

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gg.essential.universal.UChat
import it.kada49.Constants.PREFIX
import it.kada49.Constants.VERSION
import net.minecraft.client.Minecraft
import net.minecraftforge.common.ForgeVersion
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import java.net.URL


class Update {

    private val latest: String
    private val beta: String

    private val minecraft = Minecraft.getMinecraft()
    private var hasTriggered = false

    init {
        var result = ""
        try {
            result = URL("https://kada49.github.io/json/FPS-Display-updateJson.json").readText()
        } catch (error: Error) {
            UChat.chat("An error occurred requesting from 'https://kada49.github.io/json/FPS-Display-updateJson.json': " + error.localizedMessage)
        }

        val updateJson = JsonParser().parse(result) as JsonObject
        this.latest = updateJson["promos"].asJsonObject["1.8.9-recommended"].asString
        this.beta = updateJson["promos"].asJsonObject["1.8.9-latest"].asString
    }

    fun checkForModUpdates(upToDateNotification: Boolean = false) {
        val modContainer = Loader.instance().activeModContainer()
        val versionStatus = ForgeVersion.getResult(modContainer)

        // Sends the correct message to the player according to the current version and the latest and recommended available version.
        // Up to date!
        if (versionStatus.status == ForgeVersion.Status.UP_TO_DATE && upToDateNotification) {
            UChat.chat("$PREFIX §fMod UP-TO-DATE! (§e$VERSION§f)")
            return
        }

        // Outdated!
        if (versionStatus.status == ForgeVersion.Status.OUTDATED) {
            UChat.chat("$PREFIX §fVersion §e$latest §favailable! /fps -> Links -> GitHub")
            return
        }

        // Future
        if (versionStatus.status == ForgeVersion.Status.AHEAD) {
            UChat.chat("$PREFIX §fWhy are you in the future?!?")
            return
        }

        // On latest Beta!
        if (versionStatus.status == ForgeVersion.Status.BETA) {
            UChat.chat("$PREFIX §fYou are currently on the latest BETA version! (§e$VERSION§f)")
            return
        }

        // New Beta available
        if (versionStatus.status == ForgeVersion.Status.BETA_OUTDATED) {
            UChat.chat("$PREFIX §fA newer BETA Version §e$latest §favailable! /fps -> Links -> GitHub")
            return
        }
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