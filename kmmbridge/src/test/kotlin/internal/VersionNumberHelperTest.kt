package co.touchlab.faktory.internal

import co.touchlab.faktory.maxVersion
import co.touchlab.faktory.prepVersionString
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class VersionNumberHelperTest {
    @Test
    fun prepVersionStringBadFormats(){
        assertFails { prepVersionString("") }
        assertFails { prepVersionString(" ") }
        assertFails { prepVersionString(".") }
        assertFails { prepVersionString("\t") }
        assertFails { prepVersionString("\n") }
    }

    @Test
    fun testSomeVersions(){
        assertEquals(0, maxVersion("0.1", emptySequence()))
        assertEquals(1, maxVersion("0.1", sequenceOf("0.1.0", "0.1.1")))
        assertEquals(199, maxVersion("0.1", sequenceOf("0.1.0", "0.1.199")))
        assertEquals(0, maxVersion("0.1", sequenceOf("0.1.0", "0.2.1")))
        assertEquals(0, maxVersion("0.1", sequenceOf("dev", "", "0.1.-42", "0.1.steve", "0.1\n\t.22")))
    }
}