plugins {
    kotlin("jvm") version "1.5.30"
}

repositories {
    mavenCentral()
}

group = "dev.ustits"
version = "1.0.0-SNAPSHOT"

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")
    testImplementation("io.kotest:kotest-property:4.6.3")
}

