import gg.essential.gradle.util.versionFromBuildIdAndBranch

plugins {
    kotlin("jvm") version "1.7.21" apply false
    id("gg.essential.multi-version.root")
}

version = versionFromBuildIdAndBranch()

preprocess {
    "1.8.9"(10809, "srg")
}