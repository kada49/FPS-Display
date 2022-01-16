package it.kada49.fpsDisplay

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.*
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.io.File


object Configuration: Vigilant(file = File("./config/${Constants.Data.ID}.toml"), guiTitle = "${Constants.Data.NAME} (${Constants.Data.VERSION})", sortingBehavior = ConfigSorting()) {

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle FPS Display",
        description = "Enable or Disable the FPS Display",
        category = "Personalisation"
    )
    var toggleSwitch = true

    @Property(
        type = PropertyType.COLOR,
        name = "Color of the FPS Display text",
        description = "Select a color from the whole palette to match the FPS Display text onscreen.",
        category = "Personalisation"
    )
    var fpsColor = Color(255, 255, 255)

    @Property(
        type = PropertyType.SWITCH,
        name = "Shadow to the FPS Display text",
        description = "Enable or disable a shadow to the FPS Display text.",
        category = "Personalisation"
    )
    var shadowSwitch = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle prefix",
        description = "Enable or disable the prefix before the FPS Counter.",
        category = "Personalisation"
    )
    var prefixSwitch = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle suffix",
        description = "Enable or disable the 'FPS' suffix after the FPS Counter.",
        category = "Personalisation"
    )
    var  suffixSwitch = true

    @Property(
        type = PropertyType.BUTTON,
        name = "GitHub",
        description = "Official download of the Mod and official opensource source code download.",
        placeholder = "Open",
        category = "Links"
    )
    @Suppress("unused") fun gitHubButton() = Utils.openUrl("https://GitHub.com/kada49/FPS-Display")

    @Property(
        type = PropertyType.BUTTON,
        name = "Discord",
        description = "Join the official Discord Server to suggest features, discuss and report bugs and discuss!",
        placeholder = "Join",
        category = "Links"
    )
    @Suppress("unused") fun discordButton() = Utils.openUrl("https://Discord.gg/DtB3vEfX4R")


    init { initialize() }

}

class ConfigSorting : SortingBehavior() {
    @NotNull
    override fun getCategoryComparator(): Comparator<Category> {
        return Comparator { o1: Category, o2: Category ->
            if (o1.name == "Personalisation") { return@Comparator -1 }
            if (o2.name == "Personalisation") { return@Comparator 1 }
            return@Comparator o1.name.compareTo(o2.name)
        }
    }
    @NotNull
    override fun getPropertyComparator(): Comparator<in PropertyData> {
        return Comparator {o1: PropertyData, o2: PropertyData ->
            if (o1.attributesExt.name == "GitHub") { return@Comparator -1 }
            if (o1.attributesExt.name == "Toggle FPS Display") { return@Comparator -1 }
            if (o2.attributesExt.name == "GitHub") { return@Comparator 1 }
            if (o2.attributesExt.name == "Toggle FPS Display") { return@Comparator 1 }
            return@Comparator o1.attributesExt.name.compareTo(o2.attributesExt.name)
        }
    }
}