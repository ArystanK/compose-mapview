pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenLocal()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

includeBuild("../include-model") {
    dependencySubstitution {
        substitute(module("com.map:model")).using(project(":"))
    }
}
includeBuild("../include-config") {
    dependencySubstitution {
        substitute(module("com.map:config")).using(project(":"))
    }
}
includeBuild("../include-tile-image") {
    dependencySubstitution {
        substitute(module("com.map:tile-image")).using(project(":"))
    }
}
