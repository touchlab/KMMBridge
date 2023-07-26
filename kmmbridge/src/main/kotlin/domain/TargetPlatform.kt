package co.touchlab.faktory.domain

internal data class TargetPlatform(
    val version: PlatformVersion,
    val targets: List<TargetName>
)