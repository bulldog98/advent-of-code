plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

val kotlinVersion = "2.1.0"

dependencies {
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation("org.jetbrains.kotlin.plugin.allopen", "org.jetbrains.kotlin.plugin.allopen.gradle.plugin", kotlinVersion)
    implementation("org.jetbrains.kotlin.plugin.serialization", "org.jetbrains.kotlin.plugin.serialization.gradle.plugin", kotlinVersion)
    implementation("org.jetbrains.kotlinx.benchmark:org.jetbrains.kotlinx.benchmark.gradle.plugin:0.4.13")
}