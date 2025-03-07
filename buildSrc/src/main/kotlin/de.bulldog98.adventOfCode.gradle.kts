import org.gradle.accessors.dm.LibrariesForLibs

// needed because of https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("de.bulldog98.kotlin")
    id("de.bulldog98.data")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
}

dependencies {
    implementation(project(":common"))
    implementation(libs.kotlinx.serialization)
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