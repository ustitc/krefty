plugins {
    kotlin("jvm") version "1.5.30"
}

repositories {
    mavenCentral()
}

group = "dev.ustits"
version = "0.1.0-SNAPSHOT"

tasks.withType<Test> {
    useJUnitPlatform()
}

val kotestVersion = "4.6.3"

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

