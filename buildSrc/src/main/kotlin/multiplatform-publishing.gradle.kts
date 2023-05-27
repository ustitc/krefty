plugins {
    signing
    `java-library`
    `maven-publish`
}

val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

val javadoc = tasks.named("javadoc")

group = "dev.ustits.krefty"
version = Ci.version

val javadocJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles java doc to jar"
    archiveClassifier.set("javadoc")
    from(javadoc)
}

publishing {
    publications.withType<MavenPublication>().forEach {
        it.apply {
            artifact(javadocJar)
        }
    }
}

val signingKey: String? by project
val signingPassword: String? by project

signing {
    useGpgCmd()
    if (signingKey != null && signingPassword != null) {
        @Suppress("UnstableApiUsage")
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
    if (Ci.isRelease) {
        sign(publications)
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

    publications.withType<MavenPublication>().forEach {
        it.apply {
            pom {
                name.set("Krefty")
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
                    url.set("https://github.com/ustits/krefty/tree/main")
                }
            }
        }
    }
}