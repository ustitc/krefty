plugins {
    id("maven-publish")
    id("signing")
}

allprojects {

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "dev.ustits.krefty"
    version = Ci.version

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }

    signing {
        val signingKey: String? by project
        val signingPassword: String? by project
        if (signingKey != null && signingPassword != null) {
            useInMemoryPgpKeys(signingKey, signingPassword)
        }
        if (Ci.isRelease) {
            sign(publishing.publications)
        }
    }

    publishing {
        repositories {
            maven {
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                name = "sonatype"
                url = if (Ci.isRelease) releasesRepoUrl else snapshotsRepoUrl
                credentials {
                    username = System.getenv("OSSRH_USERNAME") ?: ""
                    password = System.getenv("OSSRH_PASSWORD") ?: ""
                }
            }
        }

        publications.withType<MavenPublication> {
            artifact(javadocJar.get())
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

                scm {
                    connection.set("scm:git:git://github.com/ustits/krefty.git")
                    developerConnection.set("scm:git:ssh://github.com:ustits/krefty.git")
                    url.set("http://github.com/ustits/krefty/tree/main")
                }
            }
        }
    }
}
