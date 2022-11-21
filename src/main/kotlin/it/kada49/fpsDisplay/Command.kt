package it.kada49.fpsDisplay

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos

class Command: CommandBase() {
    override fun getCommandName() = "fps"
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getCommandUsage(sender: ICommandSender?): String = "/fps = Setup for the ${Constants.NAME} Mod configuration"

    override fun addTabCompletionOptions(sender: ICommandSender?, args: Array<String>, pos: BlockPos): MutableList<String>? {
        if (args.size == 1) { return getListOfStringsMatchingLastWord(args, mutableListOf("update")) }
        return null
    }

    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (args.isEmpty()) {
            FpsDisplayMod.vigilantGui = Configuration.gui()
            return
        }
        if (args[0] == "update") { Update().checkForModUpdates(true) }
    }
}