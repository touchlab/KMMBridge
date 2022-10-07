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
        assertEquals(0, maxVersion("0.1", emptyList<String>().iterator()))
        assertEquals(1, maxVersion("0.1", listOf("0.1.0", "0.1.1").iterator()))
        assertEquals(199, maxVersion("0.1", listOf("0.1.0", "0.1.199").iterator()))
        assertEquals(0, maxVersion("0.1", listOf("0.1.0", "0.2.1").iterator()))
        assertEquals(0, maxVersion("0.1", listOf("dev", "", "0.1.-42", "0.1.steve", "0.1\n\t.22").iterator()))
    }
}