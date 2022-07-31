pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
    }

    plugins {
        val egtVersion = "0.1.11"
        id("gg.essential.multi-version.root") version egtVersion
    }
}

rootProject.buildFileName = "root.gradle.kts"
rootProject.name = "FPS-Display"

val mcVersion = "1.8.9"

include(":$mcVersion")
project(":$mcVersion").apply {
    projectDir = file("versions/$mcVersion")
    buildFileName = "../../build.gradle.kts"
}