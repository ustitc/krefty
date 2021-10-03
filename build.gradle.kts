plugins {
    kotlin("jvm") version "1.5.30" apply false
    id("io.gitlab.arturbosch.detekt") version "1.18.1" apply false
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.ustits.krefty"
    version = Ci.version

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "1.8"
    }

}
