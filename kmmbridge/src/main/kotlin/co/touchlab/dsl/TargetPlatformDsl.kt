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

    fun platforms(block: List<TargetPlatform>.() -> Unit) {
        platforms.apply(block)
    }

    fun iOS(version: String = "17") {
        platforms.add(TargetPlatform(PlatformName.IOS, PlatformVersion(version)))
    }

    fun macOS(version: String = "13") {
        platforms.add(TargetPlatform(PlatformName.MacOS, PlatformVersion(version)))
    }

    fun tvOS(version: String = "17.3") {
        platforms.add(TargetPlatform(PlatformName.TvOS, PlatformVersion(version)))
    }

    fun watchOS(version: String = "9") {
        platforms.add(TargetPlatform(PlatformName.WatchOS, PlatformVersion(version)))
    }

    fun getPlatformsAsFormattedText(): String {
        if (platforms.isEmpty()) {
            iOS()
        }

        val formattedPlatforms = platforms.joinToString(",\n") { platform ->
            "        .${platform.name.value}(.v${platform.version.version})"
        }
        return formattedPlatforms
    }

}