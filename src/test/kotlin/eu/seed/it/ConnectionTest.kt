package eu.seed.it

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ServerTest {

    @Test
    fun `port in range`() {
        val port = Port(3306)
        assertTrue(port.validate())
    }

    @Test
    fun `port out of range (negative)`() {
        val port = Port(-29292)
        assertFalse(port.validate())
    }

    @Test
    fun `port out of range (too big)`() {
        val port = Port(100000)
        assertFalse(port.validate())
    }

}
