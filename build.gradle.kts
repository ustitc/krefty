plugins {
    kotlin("jvm") version "1.5.30"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
}

repositories {
    mavenCentral()
}

group = "dev.ustits"
version = "0.1.0-SNAPSHOT"

tasks.withType<Test> {
    useJUnitPlatform()
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    reports {
        html.enabled = false
        xml.enabled = false
        txt.enabled = true
        sarif.enabled = false
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "11"
}

val kotestVersion = "4.6.3"

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}
