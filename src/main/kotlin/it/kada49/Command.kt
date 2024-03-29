package it.kada49

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import net.minecraft.util.BlockPos

class Command : CommandBase() {
    override fun getCommandName() = "fps"
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getCommandUsage(sender: ICommandSender?): String = "/fps = Setup for the FPS-Display Mod configuration\n/fps update = Check for mod updates"
    override fun addTabCompletionOptions(
        sender: ICommandSender?,
        args: Array<String>,
        pos: BlockPos
    ): MutableList<String>? {
        if (args.size == 1) {
            return getListOfStringsMatchingLastWord(args, mutableListOf("update"))
        }

        return null
    }

    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (args.isEmpty()) {
            FPSDisplay.gui = Configuration.gui()
            return
        }

        if (args[0] == "update") {
            Update().checkForModUpdates(true)
            return
        }

        throw WrongUsageException(getCommandUsage(sender))
    }
}