package co.touchlab.faktory.dependencyManager

import co.touchlab.faktory.dependencymanager.SpmConfig
import org.junit.Test
import kotlin.test.assertEquals

class SpmConfigTest {

    @Test
    fun addMultiplePlatforms() {
        val config = SpmConfig().apply(fun SpmConfig.() {
            platforms {
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
        })

        assertEquals("""
        .iOS(.v14),
        .macOS(.v13),
        .watchOS(.v7),
        .tvOS(.v14)""".trimMargin(), config.getPlatformsAsFormattedText())
    }


    @Test
    fun addWatchOSToPlatforms() {
        val config = SpmConfig().apply(fun SpmConfig.() {
            platforms {
                watchOS {
                    v("7")
                }
            }
        })

        assertEquals(".watchOS(.v7)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addTvOSToPlatforms() {
        val config = SpmConfig().apply(fun SpmConfig.() {
            platforms {
                tvOS {
                    v("14")
                }
            }
        })

        assertEquals(".tvOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addMacOSToPlatforms() {
        val config = SpmConfig().apply(fun SpmConfig.() {
            platforms {
                macOS {
                    v("13")
                }
            }
        })

        assertEquals(".macOS(.v13)", config.getPlatformsAsFormattedText().trimIndent())
    }

    @Test
    fun addIOSToPlatforms() {
        val config = SpmConfig().apply(fun SpmConfig.() {
            platforms {
                iOS {
                    v("14")
                }
            }
        })

        assertEquals(".iOS(.v14)", config.getPlatformsAsFormattedText().trimIndent())
    }
}