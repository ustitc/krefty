plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    jvm {
        withJava()
    }
    js(IR) {
        nodejs()
    }
}