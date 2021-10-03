object Ci {

    private const val snapshotBase = "0.1.0"

    private val githubRunNumber = System.getenv("GITHUB_RUN_NUMBER")

    private val snapshotVersion = when (githubRunNumber) {
        null -> "$snapshotBase-LOCAL"
        else -> "$snapshotBase.${githubRunNumber}-SNAPSHOT"
    }

    private val releaseVersion = System.getenv("RELEASE_VERSION")

    val isRelease = releaseVersion != null
    val version = releaseVersion ?: snapshotVersion
}
