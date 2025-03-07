val coroutinesVersion : String by rootProject.extra
val kotlinVersion : String by rootProject.extra
val junitVersion : String by rootProject.extra

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}