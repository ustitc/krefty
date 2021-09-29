plugins {
    id("java-library")
    id("maven-publish")
    id("io.gitlab.arturbosch.detekt")
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

publishing {
    publications {
        create<MavenPublication>("myLibrary") {
            from(components["kotlin"])
            pom {
                name.set("krefty")
                description.set("Refined types for Kotlin")
                url.set("https://github.com/ustits/krefty")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://mit-license.org")
                    }
                }

                developers {
                    developer {
                        id.set("ustits")
                        name.set("Ruslan Ustits")
                        email.set("ustitsruslan@gmail.com")
                    }
                }
            }

        }
    }
}

val kotestVersion = "4.6.3"

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}
