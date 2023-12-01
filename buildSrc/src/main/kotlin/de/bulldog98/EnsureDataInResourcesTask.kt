package de.bulldog98

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

abstract class EnsureDataInResourcesTask : DefaultTask() {
    @get:InputDirectory
    abstract val data: DirectoryProperty

    @TaskAction
    fun symlinkToProjectDir() {
        val dir = data.asFile.get()
        val destination = project.layout.projectDirectory.asFile.resolve("data")
        if (!destination.exists()) {
            Files.createSymbolicLink(destination.toPath(), dir.toPath())
        }
    }
}
