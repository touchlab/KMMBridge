package co.touchlab.domain

data class TargetPlatform(
    val name: PlatformName,
    val version: PlatformVersion,
)

enum class PlatformName(val value: String) {
    MacOS("macOS"),
    IOS("iOS"),
    TvOS("tvOS"),
    WatchOS("watchOS"),
}

class PlatformVersion {
    var version: String? = null

    fun v(version: String) {
        this.version = version
    }

    fun default(version: String): PlatformVersion {
        if (this.version == null) {
            this.version = version
        }
        return this
    }
}