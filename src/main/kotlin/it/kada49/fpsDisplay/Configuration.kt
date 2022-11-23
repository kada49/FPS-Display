package it.kada49.fpsDisplay

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.*
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.io.File


object Configuration : Vigilant(
    file = File("./config/${Constants.ID}.toml"),
    guiTitle = "${Constants.NAME} (${Constants.VERSION})",
    sortingBehavior = ConfigSorting()
) {

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
        name = "Shadow to the FPS Counter text",
        description = "Enable or disable a shadow to the FPS Counter.",
        category = "Personalisation"
    )
    var shadowSwitch = true

    @Property(
        type = PropertyType.SELECTOR,
        name = "Positioning",
        description = "Select one of the available positioning for the FPS Counter.",
        options = ["Top Left", "Top Middle", "Top Right", "Bottom Left", "Bottom Right"],
        category = "Personalisation"
    )
    var positionSelector = 0

    @Property(
        type = PropertyType.SLIDER,
        name = "Scale",
        description = "Change the scale of the FPS Counter.",
        min = 1,
        max = 3,
        category = "Personalisation"
    )
    var scaleSlider = 1

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle brackets",
        description = "Enable or disable brackets around all the FPS Counter text",
        category = "Personalisation",
        subcategory = "Text Formatting"
    )
    var bracketsSelector = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle prefix",
        description = "Enable or disable the '[FPS]' prefix before the FPS Number.",
        category = "Personalisation",
        subcategory = "Text Formatting"
    )
    var prefixSwitch = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle suffix",
        description = "Enable or disable the 'FPS' suffix after the FPS Number.",
        category = "Personalisation",
        subcategory = "Text Formatting"
    )
    var suffixSwitch = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle Background",
        description = "Enable or disable a background color.",
        category = "Personalisation",
        subcategory = "Background"
    )
    var backgroundSwitch = true

    @Property(
        type = PropertyType.COLOR,
        name = "Color of the Background",
        description = "Pick a color for for the background.",
        category = "Personalisation",
        subcategory = "Background"
    )
    var backgroundColor = Color(211, 211, 211)

    @Property(
        type = PropertyType.SLIDER,
        name = "Transparency",
        description = "Set an alpha value for the background.",
        min = 1,
        max = 255,
        category = "Personalisation",
        subcategory = "Background"
    )
    var alphaSlider = 64

    @Property(
        type = PropertyType.SLIDER,
        name = "Outer edge of the background",
        description = "Set the value for the size of the edge over the FPS Counter.",
        min = 2,
        max = 4,
        category = "Personalisation",
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
    fun gitHubButton() = Utils.openUrl("https://GitHub.com/kada49/FPS-Display")

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
        listOf("edgeSlider", "alphaSlider", "backgroundColor")
            .forEach { addDependency(it, "backgroundSwitch") }
    }

}

class ConfigSorting : SortingBehavior() {
    @NotNull
    override fun getCategoryComparator(): Comparator<Category> {
        return Comparator { o1: Category, o2: Category ->

            /**
             * Personalisation category
             */
            if (o1.name == "Personalisation") {
                return@Comparator -1
            }
            if (o2.name == "Personalisation") {
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