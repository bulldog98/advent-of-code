package de.bulldog98

import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.task

interface DataPluginExtension {
    val dataPath: DirectoryProperty
}

class DataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<DataPluginExtension>("data-plugin")
        val rootDataDir = target.rootDir.resolve("data")
        val defaultDataDir = target.objects.directoryProperty().fileValue(rootDataDir)
        extension.dataPath.convention(defaultDataDir)

        target.task<EnsureDataInResourcesTask>("ensureDataInResources") {
            data.set(extension.dataPath)
        }

        target.tasks.findByName("classes")?.dependsOn?.add("ensureDataInResources")
    }
}