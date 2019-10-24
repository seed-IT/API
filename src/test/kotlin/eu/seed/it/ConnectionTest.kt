package eu.seed.it

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ServerTest {

    @Test
    fun `port in range`() {
        val connection = Connection("localhost", 3306)
        assertTrue(connection.validate())
    }

    @Test
    fun `port out of range (negative)`() {
        val connection = Connection("localhost", -29292)
        assertFalse(connection.validate())
    }

    @Test
    fun `port out of range (too big)`() {
        val connection = Connection("localhost", 100000)
        assertFalse(connection.validate())
    }

}
