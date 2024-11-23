package co.touchlab.kmmbridge.internal.domain

import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.KonanTarget

internal val Family.swiftPackagePlatformName
    get() = when (this) {
        Family.OSX -> "macOS"
        Family.IOS -> "iOS"
        Family.TVOS -> "tvOS"
        Family.WATCHOS -> "watchOS"
        else -> null
    }

internal val TargetName.konanTarget: KonanTarget
    get() = when (this) {
        TargetName.IOSarm64 -> KonanTarget.IOS_ARM64
        TargetName.IOSx64 -> KonanTarget.IOS_X64
        TargetName.IOSSimulatorArm64 -> KonanTarget.IOS_SIMULATOR_ARM64
        TargetName.watchosArm32 -> KonanTarget.WATCHOS_ARM32
        TargetName.watchosArm64 -> KonanTarget.WATCHOS_ARM64
        TargetName.watchosX64 -> KonanTarget.WATCHOS_X64
        TargetName.watchosSimulatorArm64 -> KonanTarget.WATCHOS_SIMULATOR_ARM64
        TargetName.tvosArm64 -> KonanTarget.TVOS_ARM64
        TargetName.tvosX64 -> KonanTarget.TVOS_X64
        TargetName.tvosSimulatorArm64 -> KonanTarget.TVOS_SIMULATOR_ARM64
        TargetName.macosX64 -> KonanTarget.MACOS_X64
        TargetName.macosArm64 -> KonanTarget.MACOS_ARM64
        TargetName.watchosDeviceArm64 -> KonanTarget.WATCHOS_DEVICE_ARM64
    }