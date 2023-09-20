package co.touchlab.faktory.dsl

import co.touchlab.faktory.domain.PlatformVersion
import co.touchlab.faktory.domain.TargetName
import co.touchlab.faktory.domain.TargetPlatform
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