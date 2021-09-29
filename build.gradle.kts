plugins {
    kotlin("jvm") version "1.5.30" apply false
    id("io.gitlab.arturbosch.detekt") version "1.18.1" apply false
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.ustits.krefty"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "11"
    }

}
