package dsl

import co.touchlab.dsl.TargetPlatformDsl
import org.junit.Test
import kotlin.test.assertEquals

class TargetPlatformDslTest {

    /**
     * for config: spm()
     */
    @Test
    fun defaultIosPlatform() {
        val config = TargetPlatformDsl()
        assertEquals(
            """
        .iOS(.v17)""".trimMargin(), config.getPlatformsAsFormattedText()
        )
    }

    /**
     * for config:
     * spm {
     *         platforms {
     *             iOS()
     *             macOS()
     *             watchOS()
     *             tvOS()
     *         }
     *     }
     */
    @Test
    fun defaultPlatforms() {
        val config = TargetPlatformDsl().apply {
            iOS()
            macOS()
            watchOS()
            tvOS()
        }

        assertEquals(
            """
        .iOS(.v17),
        .macOS(.v13),
        .watchOS(.v9),
        .tvOS(.v17.3)""".trimMargin(), config.getPlatformsAsFormattedText()
        )
    }

    /**
     * for config:
     * spm {
     *         platforms {
     *             iOS("14")
     *             macOS("13")
     *             watchOS("7")
     *             tvOS("14")
     *         }
     *     }
     */
    @Test
    fun addMultiplePlatforms() {
        val config = TargetPlatformDsl().apply {
            platforms {
                iOS("14")
                macOS("13")
                watchOS("7")
                tvOS("14")
            }
        }

        assertEquals(
            """
        .iOS(.v14),
        .macOS(.v13),
        .watchOS(.v7),
        .tvOS(.v14)""".trimMargin(), config.getPlatformsAsFormattedText()
        )
    }


    /**
     * for config:
     * spm {
     *         platforms {
     *             watchOS("7")
     *         }
     *     }
     */
    @Test
    fun addWatchOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            platforms {
                watchOS("7")
            }
        }

        assertEquals(".watchOS(.v7)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *         platforms {
     *             tvOS("14")
     *         }
     *     }
     */
    @Test
    fun addTvOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            platforms {
                tvOS("14")
            }
        }

        assertEquals(".tvOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *         platforms {
     *             macOS("13")
     *         }
     *     }
     */
    @Test
    fun addMacOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            platforms {
                macOS("13")
            }
        }

        assertEquals(".macOS(.v13)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *         platforms {
     *             iOS("14")
     *         }
     *     }
     */
    @Test
    fun addIOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            platforms {
                iOS("14")
            }
        }

        assertEquals(".iOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }
}