import org.gradle.accessors.dm.LibrariesForLibs

repositories {
    mavenCentral()
}

// needed because of https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform)
}