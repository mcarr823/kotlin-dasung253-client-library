package dev.mcarr.d253

import kotlin.test.Test
import kotlin.test.assertFalse

/**
 * Tests the port-listing class.
 * */
class D253SerialPortListTest {

    /**
     * Try to list some ports by using the jserialcomm library.
     *
     * Note that this test doesn't know how many ports it should find.
     * It only fails if the library crashes.
     * */
    @Test
    fun testListPorts(){

        val crashed = try {
            val ports = D253SerialPortList().get()
            print("Found ${ports.size} serial ports")
            false
        }catch (e: Exception){
            true
        }
        assertFalse(crashed, "Test crashed while trying to list ports")

    }

}