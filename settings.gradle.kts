pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { kotlin("multiplatform") version "2.3.21" }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ratatui-macros-kotlin"

val ratatuiLocal = file("../ratatui-kotlin")
if (ratatuiLocal.isDirectory) {
    includeBuild(ratatuiLocal) {
        dependencySubstitution {
            substitute(module("io.github.kotlinmania:ratatui-kotlin")).using(project(":"))
        }
    }
}
