package co.touchlab.faktory.dependencyManager

import co.touchlab.faktory.dependencymanager.SpmConfig
import org.junit.Test
import kotlin.test.assertEquals

class SpmConfigTest {

    @Test
    fun defaultPlatforms() {
        val config = SpmConfig().apply {
            platforms {
                iOS()
                macOS()
                watchOS()
                tvOS()
            }
        }

        assertEquals("""
        .iOS(.v17),
        .macOS(.v13),
        .watchOS(.v9),
        .tvOS(.v17.3)""".trimMargin(), config.getPlatformsAsFormattedText())
    }

    @Test
    fun addMultiplePlatforms() {
        val config = SpmConfig().apply {
            platforms {
                iOS("14")
                macOS("13")
                watchOS("7")
                tvOS("14")
            }
        }

        assertEquals("""
        .iOS(.v14),
        .macOS(.v13),
        .watchOS(.v7),
        .tvOS(.v14)""".trimMargin(), config.getPlatformsAsFormattedText())
    }


    @Test
    fun addWatchOSToPlatforms() {
        val config = SpmConfig().apply {
            platforms {
                watchOS("7")
            }
        }

        assertEquals(".watchOS(.v7)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addTvOSToPlatforms() {
        val config = SpmConfig().apply {
            platforms {
                tvOS("14")
            }
        }

        assertEquals(".tvOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addMacOSToPlatforms() {
        val config = SpmConfig().apply {
            platforms {
                macOS("13")
            }
        }

        assertEquals(".macOS(.v13)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addIOSToPlatforms() {
        val config = SpmConfig().apply {
            platforms {
                iOS("14")
            }
        }

        assertEquals(".iOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }
}