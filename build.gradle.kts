val ktorVersion by extra { "2.1.3" }
val coroutinesVersion by extra { "1.6.4" }

plugins {
    kotlin("jvm") version "1.7.22"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.22"
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

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    if (name != "common") {
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization" )

        val copyForTest = task<Copy>("copyDataForTests") {
            from("$rootDir/data/**.txt")
            into("$projectDir/data")
        }

        tasks.getByName<Test>("test") {
            dependsOn.add(copyForTest)
        }

        sourceSets {
            main {
                resources {
                    includes += "**.txt"
                }
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

        if (name != "common") {
            implementation(project(":common"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
        }

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
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
