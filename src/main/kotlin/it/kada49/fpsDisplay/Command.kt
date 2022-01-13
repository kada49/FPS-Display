package it.kada49.fpsDisplay

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class Command: CommandBase() {
    override fun getCommandName() = "fps"
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getCommandUsage(sender: ICommandSender?): String = "/fps = Setup for the ${Constants.Data.NAME} Mod configuration"
    override fun processCommand(sender: ICommandSender?, args: Array<String>) { MainMod.gui = Configuration.gui() }
}