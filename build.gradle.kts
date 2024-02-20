import dev.architectury.pack200.java.Pack200Adapter
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version("1.9.22")
    id("gg.essential.loom") version("1.3.12")
    id("dev.architectury.architectury-pack200") version ("0.1.3")
}

val modGroup: String by project
val modBaseName: String by project
val modVersion: String by project

group = modGroup
version = modVersion
base.archivesName.set(modBaseName)

loom {
    runConfigs {
        getByName("client") {
            programArgs("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
    forge.pack200Provider.set(Pack200Adapter())
}

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
}

val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")


    embed("gg.essential:loader-launchwrapper:1.2.2")
    compileOnly("gg.essential:essential-1.8.9-forge:16261+gb6125ee84b")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("mcmod.info") {
            expand("version" to project.version)
        }
    }

    jar {
        from(embed.files.map { zipTree(it) })

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
        options.encoding = "UTF-8"
    }
}