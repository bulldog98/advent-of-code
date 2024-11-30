val ktorVersion by extra { "2.3.6" }
val coroutinesVersion by extra { "1.7.3" }

plugins {
    kotlin("jvm") version "2.1.0"
    @Suppress("SpellCheckingInspection")
    id("org.jetbrains.kotlin.plugin.allopen") version "2.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.9"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    @Suppress("SpellCheckingInspection")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlinx.benchmark")

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    if (name.startsWith("advent")) {
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization" )

        dependencies {
            implementation(project(":common"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    }

    allOpen {
        annotation("org.openjdk.jmh.annotations.State")
    }

    benchmark {
        targets {
            register("main") {
                this as kotlinx.benchmark.gradle.JvmBenchmarkTarget
                jmhVersion = "1.21"
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
