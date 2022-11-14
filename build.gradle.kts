import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
val modVersion: String by project

group = modGroup
version = modVersion
base.archivesName.set(modBaseName)

loom {
    noServerRunConfigs()
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
}

val include: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(include)

dependencies {
    include("gg.essential:loader-launchwrapper:1.1.3")
    implementation("gg.essential:essential-$platform:10975+g81b631012")
}

tasks.jar {
    from(include.files.map { zipTree(it) })

    manifest.attributes(
        mapOf(
            "ModSide" to "CLIENT",
            "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
        )
    )
}