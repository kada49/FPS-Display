import dev.architectury.pack200.java.Pack200Adapter
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version("1.8.21")
    id("gg.essential.loom") version("0.10.0.+")
    id("dev.architectury.architectury-pack200") version ("0.1.3")
}

val modGroup: String by project
val modBaseName: String by project
val modVersion: String by project

group = modGroup
version = modVersion
base.archivesName.set(modBaseName)

loom {
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
    forge.pack200Provider.set(Pack200Adapter())
}

val include: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(include)

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")


    include("gg.essential:loader-launchwrapper:1.2.0")
    implementation("gg.essential:essential-1.8.9-forge:12650+g9451b4afe")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("mcmod.info") {
            expand("version" to project.version)
        }
    }

    jar {
        from(include.files.map { zipTree(it) })

        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
                "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
            )
        )
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<JavaCompile> {
        options.release.set(8)
    }
}