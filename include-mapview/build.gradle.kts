import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = "1.0-SNAPSHOT"

val KTOR_VERSION = "1.6.8"
//val KTOR_VERSION = "1.6.2-native-mm-eap-196"
val ktorCore = "io.ktor:ktor-client-core:$KTOR_VERSION"
val ktorCIO = "io.ktor:ktor-client-cio:$KTOR_VERSION"
val ktorIos = "io.ktor:ktor-client-ios:$KTOR_VERSION"
val ktorOkHttp = "io.ktor:ktor-client-okhttp:$KTOR_VERSION"

kotlin {
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.map:config:1.0-SNAPSHOT")
                implementation("com.map:model:1.0-SNAPSHOT")
                implementation("com.map:tile-image:1.0-SNAPSHOT")
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation("com.map:io-android-desktop:1.0-SNAPSHOT")
                implementation("com.map:ui-android-desktop:1.0-SNAPSHOT")
                implementation(ktorCIO)
                implementation(compose.desktop.common)
            }
        }
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

kotlin.targets.withType(KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}
