import de.bulldog98.DataPluginExtension
import de.bulldog98.EnsureDataInResourcesTask

val extension = extensions.create<DataPluginExtension>("data-plugin")
val rootDataDir = rootDir.resolve("data")
val defaultDataDir: DirectoryProperty = objects.directoryProperty().fileValue(rootDataDir)
extension.dataPath.convention(defaultDataDir)

val ensureDataInResources = tasks.register<EnsureDataInResourcesTask>("ensureDataInResources") {
    data.set(extension.dataPath)
}

tasks.findByName("classes")?.run {
    dependsOn(ensureDataInResources)
}