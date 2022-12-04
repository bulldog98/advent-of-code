val ktorVersion by extra { "2.1.3" }
val coroutinesVersion by extra { "1.6.4" }

plugins {
    kotlin("jvm") version "1.7.22"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.22"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
}


allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlinx.benchmark")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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
        gradleVersion = "7.6"
    }
}
