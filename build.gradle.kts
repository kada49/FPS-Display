import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.minecraftforge.gradle.forge") version "6f53277"
    kotlin("jvm") version "1.7.10"
}

version = "1.1.1"
group = "it.kada49"

minecraft {
    version = "1.8.9-11.15.1.2318-1.8.9"
    runDir = "run"
    mappings = "stable_22"
    makeObfSourceJar = false
}

val include: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(include)

repositories {
    mavenCentral()
    maven("https://repo.sk1er.club/repository/maven-public")
}

dependencies {
    include("gg.essential:loader-launchwrapper:1.1.3")
    implementation("gg.essential:essential-1.8.9-forge:4167+g4594ad6e6")
}

sourceSets.main {
    output.setResourcesDir(java.outputDir)
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("mcversion", project.minecraft.version)

        from(sourceSets.main.get().resources.srcDirs) {
            include("mcmod.info")

            expand("version" to project.version,
                "mcversion" to project.minecraft.version)
        }

        from(sourceSets.main.get().resources.srcDirs) {
            exclude("mcmod.info")
        }
    }

    shadowJar {
        archiveClassifier.set("")
        archiveBaseName.set("FPS-Display")
        configurations = listOf(include)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    jar {
        manifest.attributes(
            mapOf(
                "ForceLoadAsMod" to true,
                "ModSide" to "CLIENT",
                "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
            )
        )

        dependsOn(shadowJar)
        enabled = false
    }

    compileJava {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"

        options.encoding = "UTF-8"
    }
}

reobf.register("shadowJar") {}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"