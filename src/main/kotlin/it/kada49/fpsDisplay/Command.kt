package it.kada49.fpsDisplay

import gg.essential.universal.UChat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import kotlin.system.measureTimeMillis

class Command: CommandBase() {
    override fun getCommandName() = "fps"
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getCommandUsage(sender: ICommandSender?): String = "/fps = Setup for the ${Constants.NAME} Mod configuration"
    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (args.isEmpty()) {
            FpsDisplayMod.vigilantGui = Configuration.gui()
            return
        }
        if (args[0] == "update") { Update().checkForModUpdates(true) }
        UChat.chat(measureTimeMillis {
            if (args.isEmpty()) {
                FpsDisplayMod.vigilantGui = Configuration.gui()
                return
            }
            if (args[0] == "update") { Update().checkForModUpdates(true) }
        })
    }
}