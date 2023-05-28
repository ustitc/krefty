plugins {
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.detekt)
    id("multiplatform-build")
    id("multiplatform-publishing")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":krefty-core"))
                implementation(libs.arrow.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotest.engine)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.property)
                implementation(libs.kotest.arrow)
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotest.junit5)
            }
        }
        val jsMain by getting
        val jsTest by getting
    }
}
