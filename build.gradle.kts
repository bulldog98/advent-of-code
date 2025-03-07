plugins {
    kotlin("jvm") apply false
    id("org.jetbrains.kotlin.plugin.allopen") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    id("org.jetbrains.kotlinx.benchmark") apply false
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
