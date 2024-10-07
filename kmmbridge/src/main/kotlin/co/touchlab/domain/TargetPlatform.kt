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

data class PlatformVersion(
    val version: String = "",
)