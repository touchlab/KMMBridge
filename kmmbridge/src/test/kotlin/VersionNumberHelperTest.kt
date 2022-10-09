package co.touchlab.faktory.internal

import org.junit.Test
import kotlin.test.assertEquals

class VersionNumberHelperTest {
    /**
     * We're essentially testing a binary search algorithm. There's probably a more formal algorithm name for what
     * we're doing here.
     */
    @Test
    fun testVersionsDiscovery(){
        val versionSet = mutableSetOf<String>()

        var versionCount = 0
        while (versionCount < 5_000){
            var checkCount = 0
            val nextVersion = findNextVersion("0.1"){
                checkCount++
                versionSet.contains(it)
            }
            versionSet.add(nextVersion)
            // Print out if you want to see api call counts
//            println("nextVersion: $nextVersion, checkCount: $checkCount")
            assertEquals("0.1.$versionCount", nextVersion)

            versionCount++
        }
    }
}