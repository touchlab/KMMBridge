package co.touchlab.kmmbridge.internal.domain

internal data class TargetPlatform(
    val version: PlatformVersion,
    val targets: List<TargetName>
)