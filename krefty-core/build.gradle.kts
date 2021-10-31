plugins {
    id("io.kotest.multiplatform") version "5.0.0.5"
}

//java.sourceCompatibility = JavaVersion.VERSION_1_8

kotlin {
    jvm()
    js(IR) {
        nodejs()
    }

    val kotestVersion = "5.0.0.M3"

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:$kotestVersion")
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("io.kotest:kotest-property:$kotestVersion")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
            }
        }
        val jsMain by getting
        val jsTest by getting
    }
}
