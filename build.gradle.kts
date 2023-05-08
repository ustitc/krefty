allprojects {
    group = "dev.ustits.krefty"
    version = Ci.version

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
