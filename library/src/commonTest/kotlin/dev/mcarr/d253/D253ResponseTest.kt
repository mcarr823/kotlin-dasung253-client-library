package dev.mcarr.d253

import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253RequestCommand
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for parsing a response from the monitor.
 *
 * Note that the values being parsed are also being built by this class.
 * That's so we can run these tests without necessarily having access to
 * a Dasung253 monitor.
 * */
class D253ResponseTest {

    /**
     * Build and parse a THRESHOLD setter request.
     *
     * Check if the parsed command and argument match the values
     * specified in the request being built.
     * */
    @Test
    fun testParserSetValue(){

        val payload = D253RequestValue.Builder()
            .setCommand(D253Attribute.THRESHOLD)
            .setValue(2)
            .build()

        val request = D253Request.Builder()
            .setCommandAndPayload(payload)
            .build()

        val response = D253Response.Builder()
            .setData(request.data)
            .build()

        assertEquals(D253Attribute.THRESHOLD.value, response.cmd)
        assertEquals("02", response.arg)
        assertEquals("00", response.data1)
        assertEquals(0, response.data2.toInt())

    }

    /**
     * Build and parse a THRESHOLD getter request.
     *
     * Check if the parsed command and argument match the values
     * specified in the request being built.
     * */
    @Test
    fun testParser(){

        val request = D253Request.Builder()
            .setCommand(D253RequestCommand.GET_PARAMETER)
            .setPayload(D253Attribute.THRESHOLD)
            .build()

        val response = D253Response.Builder()
            .setData(request.data)
            .build()

        assertEquals(D253RequestCommand.GET_PARAMETER.value, response.cmd)
        assertEquals(D253Attribute.THRESHOLD.value, response.arg)
        assertEquals(0, response.data1.toInt())
        assertEquals(0, response.data2.toInt())

    }

}