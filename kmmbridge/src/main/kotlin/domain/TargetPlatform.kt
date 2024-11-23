package co.touchlab.kmmbridge.domain

internal data class TargetPlatform(
    val version: PlatformVersion,
    val targets: List<TargetName>
)