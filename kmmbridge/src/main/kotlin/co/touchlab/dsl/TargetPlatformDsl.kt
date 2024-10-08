package co.touchlab.dsl

import co.touchlab.domain.PlatformName
import co.touchlab.domain.PlatformVersion
import co.touchlab.domain.TargetPlatform

/**
 * DSL to create instances of [TargetPlatform]
 */
public class TargetPlatformDsl {
    companion object {
        const val DEFAULT = "5.9"
    }
    private var platforms = mutableListOf<TargetPlatform>()

    fun iOS(block: PlatformVersion.() -> Unit) {
        platforms.add(TargetPlatform(PlatformName.IOS, PlatformVersion().default("17").apply(block)))
    }

    fun macOS(block: PlatformVersion.() -> Unit) {
        platforms.add(TargetPlatform(PlatformName.MacOS, PlatformVersion().default("13").apply(block)))
    }

    fun tvOS(block: PlatformVersion.() -> Unit) {
        platforms.add(TargetPlatform(PlatformName.TvOS, PlatformVersion().default("17.3").apply(block)))
    }

    fun watchOS(block: PlatformVersion.() -> Unit) {
        platforms.add(TargetPlatform(PlatformName.WatchOS, PlatformVersion().default("9").apply(block)))
    }

    fun iOS(version: String = "17") {
        platforms.add(TargetPlatform(PlatformName.IOS, PlatformVersion().default(version)))
    }

    fun macOS(version: String = "13") {
        platforms.add(TargetPlatform(PlatformName.MacOS, PlatformVersion().default(version)))
    }

    fun tvOS(version: String = "17.3") {
        platforms.add(TargetPlatform(PlatformName.TvOS, PlatformVersion().default(version)))
    }

    fun watchOS(version: String = "9") {
        platforms.add(TargetPlatform(PlatformName.WatchOS, PlatformVersion().default(version)))
    }

    fun getPlatformsAsFormattedText(): String {
        if (platforms.isEmpty()) {
            iOS {}
        }

        val formattedPlatforms = platforms.joinToString(",\n") { platform ->
            "        .${platform.name.value}(.v${platform.version.version})"
        }
        return formattedPlatforms
    }

}