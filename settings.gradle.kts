pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        // Add the Forge Repository (ForgeGradle fetches most of its stuff from here)
        maven ("https://maven.minecraftforge.net") {
            name = "Forge"
        }

        // Add the Jitpack Repository (We fetch ForgeGradle from this)
        maven ("https://jitpack.io/") {
            name = "Jitpack"
        }
    }
    resolutionStrategy {
        eachPlugin {
            // If the "net.minecraftforge.gradle.forge" plugin is requested we redirect it to asbyth's ForgeGradle fork
            if (requested.id.id == "net.minecraftforge.gradle.forge") {
                useModule("com.github.asbyth:ForgeGradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "FPS-Display"