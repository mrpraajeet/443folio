plugins {
    kotlin("multiplatform") version "2.1.21"
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    js(IR) {
        browser {}
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.9.0")
            }
        }
    }
}