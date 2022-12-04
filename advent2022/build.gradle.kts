@file:Suppress("GradlePackageUpdate")

dependencies {
    implementation(project(":common"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

val copyForTest = task<Copy>("copyDataForTests") {
    from("$rootDir/data/**.txt")
    into("$projectDir/data")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    dependsOn.add(copyForTest)
}

sourceSets {
    main {
        resources {
            includes += "**.txt"
        }
    }
}
