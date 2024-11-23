package co.touchlab.kmmbridge.dsl

import co.touchlab.kmmbridge.internal.domain.PlatformVersion
import co.touchlab.kmmbridge.internal.domain.TargetName
import co.touchlab.kmmbridge.internal.domain.TargetPlatform
import groovy.lang.Closure
import org.gradle.util.internal.ConfigureUtil

/**
 * DSL to create instances of [TargetPlatform]
 */
public class TargetPlatformDsl {
    internal var targetPlatforms: MutableList<TargetPlatform> = mutableListOf()

    /**
     * Adds all iOS targets as a [TargetPlatform] using the provided [version]
     *
     * @param version builder for an instance of [PlatformVersion]
     */
    public fun iOS(version: PlatformVersionDsl.() -> Unit) {
        targetsInternal(
            listOf(
                TargetName.IOSarm64,
                TargetName.IOSx64,
                TargetName.IOSSimulatorArm64,
            ),
            version
        )
    }

    public fun iOS(version: Closure<PlatformVersionDsl>) {
        iOS { ConfigureUtil.configure(version, this) }
    }

    /**
     * Adds all macOS targets as a [TargetPlatform] using the provided [version]
     *
     * @param version builder for an instance of [PlatformVersion]
     */
    public fun macOS(version: PlatformVersionDsl.() -> Unit) {
        targetsInternal(
            listOf(
                TargetName.macosArm64,
                TargetName.macosX64,
            ),
            version
        )
    }

    public fun macOS(version: Closure<PlatformVersionDsl>) {
        macOS { ConfigureUtil.configure(version, this) }
    }

    /**
     * Adds all tvOS targets as a [TargetPlatform] using the provided [version]
     *
     * @param version builder for an instance of [PlatformVersion]
     */
    public fun tvOS(version: PlatformVersionDsl.() -> Unit) {
        targetsInternal(
            listOf(
                TargetName.tvosX64,
                TargetName.tvosArm64,
                TargetName.tvosSimulatorArm64,
            ),
            version
        )
    }

    public fun tvOS(version: Closure<PlatformVersionDsl>) {
        tvOS { ConfigureUtil.configure(version, this) }
    }

    /**
     * Adds all watchOS targets as a [TargetPlatform] using the provided [version]
     *
     * @param version builder for an instance of [PlatformVersion]
     */
    public fun watchOS(version: PlatformVersionDsl.() -> Unit) {
        targetsInternal(
            listOf(
                TargetName.watchosX64,
                TargetName.watchosArm32,
                TargetName.watchosArm64,
                TargetName.watchosDeviceArm64,
                TargetName.watchosSimulatorArm64,
            ),
            version
        )
    }

    public fun watchOS(version: Closure<PlatformVersionDsl>) {
        watchOS { ConfigureUtil.configure(version, this) }
    }

    private fun targetsInternal(names: List<TargetName>, configure: PlatformVersionDsl.() -> Unit) {
        val platformVersion: PlatformVersion = PlatformVersionDsl().apply(configure).version ?: return

        targetPlatforms.add(TargetPlatform(version = platformVersion, targets = names))
    }

    public class PlatformVersionDsl {
        internal var version: PlatformVersion? = null

        public fun v(versionName: String) {
            PlatformVersion.of(versionName)?.let { this.version = it }
        }
    }

}