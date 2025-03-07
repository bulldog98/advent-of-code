import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `kotlin-dsl`
}

// make version catalog available for convention plugins
gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach {
    dependencies.compileOnly(files(it.absolutePath))
}

fun PluginManager.alias(notation: Provider<PluginDependency>) {
    apply(notation.get().pluginId)
}
fun PluginManager.alias(notation: ProviderConvertible<PluginDependency>) {
    apply(notation.asProvider().get().pluginId)
}

// needed because of https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation(libs.kotlin.plugin.jvm)
    implementation(libs.kotlin.plugin.allopen)
    implementation(libs.kotlin.plugin.serialization)
    implementation(libs.kotlinx.plugin.benchmark)
}
