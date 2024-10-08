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
     *        iOS {}
     *        macOS {}
     *        watchOS {}
     *        tvOS {}
     *     }
     */
    @Test
    fun defaultPlatforms() {
        val config = TargetPlatformDsl().apply {
            iOS {}
            macOS {}
            watchOS {}
            tvOS {}
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
     *        iOS()
     *        macOS()
     *        watchOS()
     *        tvOS()
     *     }
     */
    @Test
    fun defaultPlatformsCompact() {
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
     *        iOS { v("14") }
     *        macOS { v("13") }
     *        watchOS { v("7") }
     *        tvOS { v("14") }
     *     }
     */
    @Test
    fun addMultiplePlatforms() {
        val config = TargetPlatformDsl().apply {
            iOS {
                v("14")
            }
            macOS {
                v("13")
            }
            watchOS {
                v("7")
            }
            tvOS {
                v("14")
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
     *        iOS("14")
     *        macOS("13")
     *        watchOS("7")
     *        tvOS("14")
     *     }
     */
    @Test
    fun addMultiplePlatformsCompact() {
        val config = TargetPlatformDsl().apply {
            iOS("14")
            macOS("13")
            watchOS("7")
            tvOS("14")
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
     *         watchOS { v("7") }
     *     }
     */
    @Test
    fun addWatchOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            watchOS {
                v("7")
            }
        }

        assertEquals(".watchOS(.v7)", config.getPlatformsAsFormattedText().trimIndent())
    }


    /**
     * for config:
     * spm {
     *         watchOS("7")
     *     }
     */
    @Test
    fun addWatchOSToPlatformsCompact() {
        val config = TargetPlatformDsl().apply {
            watchOS("7")
        }

        assertEquals(".watchOS(.v7)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *        tvOS { v("14") }
     *     }
     */
    @Test
    fun addTvOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            tvOS {
                v("14")
            }
        }

        assertEquals(".tvOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *        tvOS("14")
     *     }
     */
    @Test
    fun addTvOSToPlatformsCompact() {
        val config = TargetPlatformDsl().apply {
            tvOS("14")
        }

        assertEquals(".tvOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *        macOS { v("13") }
     *     }
     */
    @Test
    fun addMacOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            macOS {
                v("13")
            }
        }

        assertEquals(".macOS(.v13)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *        macOS("13")
     *     }
     */
    @Test
    fun addMacOSToPlatformsCompact() {
        val config = TargetPlatformDsl().apply {
            macOS("13")
        }

        assertEquals(".macOS(.v13)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *       iOS { v("14") }
     *     }
     */
    @Test
    fun addIOSToPlatforms() {
        val config = TargetPlatformDsl().apply {
            iOS {
                v("14")
            }
        }

        assertEquals(".iOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    /**
     * for config:
     * spm {
     *       iOS("14")
     *     }
     */
    @Test
    fun addIOSToPlatformsCompact() {
        val config = TargetPlatformDsl().apply {
            iOS("14")
        }

        assertEquals(".iOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }
}