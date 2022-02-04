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
        description = "Enable or Disable the FPS Counter Display",
        category = "Personalisation"
    )
    var toggleSwitch = true

    @Property(
        type = PropertyType.COLOR,
        name = "Color of the FPS Counter",
        description = "Select a color from the whole palette to match the FPS Counter text onscreen.",
        category = "Personalisation"
    )
    var fpsColor = Color(255, 255, 255)

    @Property(
        type = PropertyType.SWITCH,
        name = "Shadow to the FPS Display text",
        description = "Enable or disable a shadow to the FPS Counter.",
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
        type = PropertyType.SELECTOR,
        name = "Positioning",
        description = "Select one of the available positioning for the FPS Counter.",
        options = ["Top Left", "Top Right", "Bottom Left", "Bottom Right"],
        category = "Personalisation"
    )
    var positionSelector = 0

    @Property(
        type = PropertyType.SLIDER,
        name = "Scale",
        description = "Change the scale of the FPS Counter.",
        min = 1,
        max = 2,
        category = "Personalisation"
    )
var scaleSlider = 1

    @Property(
        type = PropertyType.BUTTON,
        name = "GitHub",
        description = "Official download of the Mod and official open source code download.",
        placeholder = "Open",
        category = "Links"
    )
    fun gitHubButton() = Utils.openUrl("https://GitHub.com/kada49/FPS-Display")

    @Property(
        type = PropertyType.BUTTON,
        name = "Discord",
        description = "If you discover any bugs, issues, or have ideas for future projects DM me on Discord: kada49#4224 !",
        placeholder = "No-Use-Button",
        category = "Links"
    )
    fun discordButton() = ""


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