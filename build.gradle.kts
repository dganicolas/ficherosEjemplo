plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.ejemploficheros"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}

tasks.jar {
    archiveFileName.set("EjemploFicheros.jar")
    manifest {
        attributes("Main-Class" to "org.ejemploficheros.MainKt")
    }
}