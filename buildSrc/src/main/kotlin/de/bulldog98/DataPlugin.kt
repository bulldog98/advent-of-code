package de.bulldog98

import org.gradle.api.file.DirectoryProperty

interface DataPluginExtension {
    val dataPath: DirectoryProperty
}