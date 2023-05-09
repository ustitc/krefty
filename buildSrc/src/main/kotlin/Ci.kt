object Ci {

    private val snapshotVersion = System.getenv("SNAPSHOT_VERSION")?.let { "$it-SNAPSHOT" } ?: "0.0.1-LOCAL"
    private val releaseVersion = System.getenv("RELEASE_VERSION")

    val isRelease = releaseVersion != null
    val version = releaseVersion ?: snapshotVersion
}
