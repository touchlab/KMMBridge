package domain

import co.touchlab.faktory.domain.TargetName
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
//    TargetName.WatchOSarm32 -> KonanTarget.WATCHOS_ARM32
//    TargetName.WatchOSarm64 -> KonanTarget.WATCHOS_ARM64
//    TargetName.WatchOSx86 -> KonanTarget.WATCHOS_X86
//    TargetName.WatchOSx64 -> KonanTarget.WATCHOS_X64
//    TargetName.WatchOSSimulatorArm64 -> KonanTarget.WATCHOS_SIMULATOR_ARM64
//    TargetName.TvOSarm64 -> KonanTarget.TVOS_ARM64
//    TargetName.TvOSx64 -> KonanTarget.TVOS_X64
//    TargetName.TvOSSimulatorArm64 -> KonanTarget.TVOS_SIMULATOR_ARM64
//    TargetName.MacOSx64 -> KonanTarget.MACOS_X64
//    TargetName.MacOSArm64 -> KonanTarget.MACOS_ARM64
    }