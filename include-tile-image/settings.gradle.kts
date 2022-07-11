pluginManagement {
    repositories {
        gradlePluginPortal()
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

//Workaround for task ":include-mapview:jsTestPackageJson"
includeBuild("../include-config") {
    dependencySubstitution {
        substitute(module("com.map:config")).using(project(":"))
    }
}
