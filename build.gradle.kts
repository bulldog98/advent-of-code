val ktorVersion by extra { "3.0.1" }
val kotlinVersion by extra { "2.1.0" }
val coroutinesVersion by extra { "1.7.3" }
val serializationVersion by extra { "1.7.3" }
val junitVersion by extra { "5.9.2" }

plugins {
    kotlin("jvm") apply false
    id("org.jetbrains.kotlin.plugin.allopen") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    id("org.jetbrains.kotlinx.benchmark") apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
