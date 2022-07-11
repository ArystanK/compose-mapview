pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        id("com.android.library").version(agpVersion)
    }
}
