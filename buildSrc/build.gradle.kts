plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

gradlePlugin {
    // Define the plugin
    val greeting by plugins.creating {
        id = "de.bulldog98.plugin.adventOfCode"
        implementationClass = "de.bulldog98.DataPlugin"
    }
}