package co.touchlab.kmmbridge.internal.domain

internal enum class TargetName(val identifier: String) {
    IOSarm64("iosArm64"),
    IOSx64("iosX64"),
    IOSSimulatorArm64("iosSimulatorArm64"),
    macosArm64("macosArm64"),
    macosX64("macosX64"),
    tvosX64("tvosX64"),
    tvosArm64("tvosArm64"),
    tvosSimulatorArm64("tvosSimulatorArm64"),
    watchosX64("watchosX64"),
    watchosArm32("watchosArm32"),
    watchosArm64("watchosArm64"),
    watchosDeviceArm64("watchosDeviceArm64"),
    watchosSimulatorArm64("watchosSimulatorArm64")
    ;
}