package it.kada49

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.*
import it.kada49.Constants.ID
import it.kada49.Constants.NAME
import it.kada49.Constants.VERSION
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.awt.Desktop
import java.io.File
import java.net.URI


object Configuration : Vigilant(
    file = File("./config/$ID.toml"),
    guiTitle = "$NAME ($VERSION)",
    sortingBehavior = ConfigSorting()
) {

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle FPS Display",
        description = "Enable or Disable the FPS Counter Display",
        category = "Personalization",
        subcategory = "General"
    )
    var toggleSwitch = true

    @Property(
        type = PropertyType.COLOR,
        name = "Text color and transparency",
        description = "Pick a color and a transparency percentage from the color palette to match the FPS Counter text onscreen.",
        category = "Personalization",
        subcategory = "General"
    )
    var fpsColor = Color(255, 255, 255)

    @Property(
        type = PropertyType.SWITCH,
        name = "Shadow to the text",
        description = "Enable or disable a shadow to the FPS Counter.",
        category = "Personalization",
        subcategory = "General"
    )
    var shadowSwitch = true

    @Property(
        type = PropertyType.SELECTOR,
        name = "Positioning",
        description = "Select one of the available positioning for the FPS Counter.",
        options = ["Top Left", "Top Middle", "Top Right", "Bottom Left", "Bottom Right"],
        category = "Personalization",
        subcategory = "General"
    )
    var positionSelector = 0

    @Property(
        type = PropertyType.SLIDER,
        name = "Scale",
        description = "Change the scale of the FPS Counter.",
        min = 1,
        max = 3,
        category = "Personalization",
        subcategory = "General"
    )
    var scaleSlider = 1

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle brackets",
        description = "Enable or disable brackets around all the FPS Counter text",
        category = "Personalization",
        subcategory = "Text Formatting"
    )
    var bracketsSelector = false

    @Property(
        type = PropertyType.SELECTOR,
        name = "Format",
        description = "Choose between a prefix, suffix or just the number.",
        options = ["Prefix", "Suffix", "Just Number"],
        category = "Personalization",
        subcategory = "Text Formatting"
    )
    var format = 0

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle Background",
        description = "Enable or disable a background color.",
        category = "Personalization",
        subcategory = "Background"
    )
    var backgroundSwitch = true

    @Property(
        type = PropertyType.COLOR,
        name = "Color and transparency",
        description = "Pick a color and the transparency for for the background.",
        category = "Personalization",
        subcategory = "Background"
    )
    var backgroundColor = Color(211, 211, 211, 64)

    @Property(
        type = PropertyType.SLIDER,
        name = "Outer edge",
        description = "Set the value for the size of the edge over the FPS Counter text.",
        min = 2,
        max = 4,
        category = "Personalization",
        subcategory = "Background"
    )
    var edgeSlider = 2

    @Property(
        type = PropertyType.BUTTON,
        name = "GitHub",
        description = "Official download of the Mod and official open source code download.",
        placeholder = "Open",
        category = "Links"
    )
    @Suppress("unused")
    fun gitHubButton() {
        try {
            Desktop.getDesktop().browse(URI("https://github.com/kada49/FPS-Display"))
        } catch (_: Exception) {}
    }

    @Property(
        type = PropertyType.BUTTON,
        name = "Discord",
        description = "If you discover any bugs, issues, or have ideas for more features DM me on Discord:",
        placeholder = "kada49#4224",
        category = "Links"
    )
    @Suppress("unused")
    fun discordButton() = ""


    init {
        initialize()

        // Hide some properties if the background is disabled.
        listOf("edgeSlider", "backgroundColor")
            .forEach { addDependency(it, "backgroundSwitch") }
    }

}

class ConfigSorting : SortingBehavior() {
    @NotNull
    override fun getCategoryComparator(): Comparator<Category> {
        return Comparator { o1: Category, o2: Category ->

            /**
             * Personalization category
             */
            if (o1.name == "Personalization") {
                return@Comparator -1
            }
            if (o2.name == "Personalization") {
                return@Comparator 1
            }


            return@Comparator o1.name.compareTo(o2.name)
        }
    }

    @NotNull
    override fun getSubcategoryComparator(): Comparator<in Map.Entry<String, List<PropertyData>>> {
        return Comparator { o1: Map.Entry<String, List<PropertyData>>, o2: Map.Entry<String, List<PropertyData>> ->

            /**
             * Background Subcategory
             */
            if (o1.key == "Background") {
                return@Comparator 1
            }
            if (o2.key == "Background") {
                return@Comparator -1
            }

            return@Comparator o1.key.compareTo(o2.key)
        }
    }

    @NotNull
    override fun getPropertyComparator(): Comparator<in PropertyData> {
        return Comparator { o1: PropertyData, o2: PropertyData ->

            /**
             * FPS Counter text
             */
            if (o1.attributesExt.name == "GitHub") {
                return@Comparator -1
            }
            if (o1.attributesExt.name == "Toggle FPS Display") {
                return@Comparator -1
            }
            if (o2.attributesExt.name == "GitHub") {
                return@Comparator 1
            }
            if (o2.attributesExt.name == "Toggle FPS Display") {
                return@Comparator 1
            }

            /**
             * FPS Counter background
             */
            if (o2.attributesExt.name == "Toggle Background") {
                return@Comparator 1
            }


            return@Comparator o1.attributesExt.name.compareTo(o2.attributesExt.name)
        }
    }
}