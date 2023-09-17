/*
 * Copyright (c) 2023 Touchlab.
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
import kotlin.test.assertFails

class VersionNumberHelperTest {
    @Test
    fun prepVersionString(){
        assertEquals(prepVersionString("1.0"), "1.0.")
        assertEquals(prepVersionString("1.0."), "1.0.")
    }

    @Test
    fun prepVersionStringBadFormats(){
        assertFails { prepVersionString("") }
        assertFails { prepVersionString(" ") }
        assertFails { prepVersionString(".") }
        assertFails { prepVersionString("\t") }
        assertFails { prepVersionString("\n") }

    }

    @Test
    fun findVersionNumber() {
        assertEquals(1, findVersionNumber("1.0.", "1.0.1"))
        assertEquals(101, findVersionNumber("1.0.", "1.0.101"))
    }

    @Test
    fun testSomeVersions(){
        assertEquals(0, maxVersion("0.1.", emptySequence()))
        assertEquals(1, maxVersion("0.1.", sequenceOf("0.1.0", "0.1.1")))
        assertEquals(199, maxVersion("0.1.", sequenceOf("0.1.0", "0.1.199")))
        assertEquals(0, maxVersion("0.1.", sequenceOf("0.1.0", "0.2.1")))
        assertEquals(0, maxVersion("0.1.", sequenceOf("dev", "", "0.1.-42", "0.1.steve", "0.1\n\t.22")))
    }
}