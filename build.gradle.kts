plugins {
    kotlin("jvm") version "1.5.30" apply false
    id("java-library")
    id("maven-publish")
    id("signing")
    id("io.gitlab.arturbosch.detekt") version "1.18.1" apply false
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    group = "dev.ustits.krefty"
    version = Ci.version
    java.sourceCompatibility = JavaVersion.VERSION_1_8

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

    java {
        withJavadocJar()
        withSourcesJar()
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

        publications {
            create<MavenPublication>("kreftyLib") {
                from(components["java"])
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

    val kotestVersion = "4.6.3"

    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
    }

}
