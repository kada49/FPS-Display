package com.kada49.fpsDisplay

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import java.io.File

object Configuration: Vigilant(file = File("./config/${Constants.Data.ID}.toml"), guiTitle = Constants.Data.NAME) {

    @Property(
        type = PropertyType.BUTTON,
        name = "GitHub",
        description = "Official download of the Mod and official opensource source code download",
        placeholder = "Open",
        category = "Links"
    )
    fun gitHubButton() = Utils.openUrl("https://github.com/kada49/FPS-Display")


    init {
        initialize()

    }

}