import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
}

tasks.withType<KotlinCompile> {
    val experimentalAnnotations = listOf(
        "kotlin.Experimental",
        "kotlin.time.ExperimentalTime"
    )
    kotlinOptions.freeCompilerArgs = experimentalAnnotations.map { "-Xuse-experimental=$it"}
    kotlinOptions.jvmTarget = "1.8"
}
