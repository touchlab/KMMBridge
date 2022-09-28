package co.touchlab.faktory

internal class TimestampVersionManager : VersionManager {
    override fun getVersion(versionPrefix: String): String = "${versionPrefix}.${System.currentTimeMillis()}"
}
