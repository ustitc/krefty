plugins {
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.detekt)
    id("multiplatform-build")
    id("multiplatform-publishing")
}

kotlin {
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(libs.kotest.engine)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.property)
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
