package it.kada49.fpsDisplay

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer

class Command: CommandBase() {
    override fun getName() = "fps"
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getUsage(sender: ICommandSender): String = "/fps = Setup for the ${Constants.NAME} Mod configuration"
    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) { FpsDisplayMod.vigilantGui = Configuration.gui() }
        if (args.isNotEmpty()) {
            if (args[0] == "update") Update().checkForModUpdates(true)
        }
    }
}