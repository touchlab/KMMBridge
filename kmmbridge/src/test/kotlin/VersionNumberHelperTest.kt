/*
 * Copyright (c) 2022 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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