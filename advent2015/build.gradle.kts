import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("de.bulldog98.adventOfCode")
}

tasks.withType(KotlinCompile::class.java).configureEach {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xnested-type-aliases"))
    }
}