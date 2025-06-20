package dev.mcarr.d253

import dev.mcarr.d253.D253Constants
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Basic tests for the defined constants.
 *
 * These tests should never fail.
 * They're more of a sanity check to make sure basic things
 * like encoding strings works correctly.
 * */
class D253ConstantsTest {

    /**
     * Tests if the packet prefix gets encoded correctly.
     * */
    @Test
    fun testPrefixValue(){
        val prefix = D253Constants.DS_COM_PREFIX
        val bytes = prefix.toCharArray().map { it.code.toByte() }
        assertEquals(53, bytes[0])
        assertEquals(70, bytes[1])
        assertEquals(70, bytes[2])
        assertEquals(53, bytes[3])
    }

    /**
     * Tests if the packet suffix gets encoded correctly.
     * */
    @Test
    fun testTailValue(){
        val prefix = D253Constants.DS_COM_TAIL
        val bytes = prefix.toCharArray().map { it.code.toByte() }
        assertEquals(65, bytes[0])
        assertEquals(48, bytes[1])
        assertEquals(70, bytes[2])
        assertEquals(65, bytes[3])
    }

}