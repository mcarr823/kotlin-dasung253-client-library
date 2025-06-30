package dev.mcarr.d253

import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253DisplayMode
import dev.mcarr.d253.enums.D253RequestCommand
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the jserialcomm serial port implementation.
 *
 * Contains several different tests which send and receive real
 * data from a real Dasung253 monitor.
 *
 * These tests require a real Dasung253 monitor to be attached via USB
 * in order to run.
 * */
class D253SerialPortTest {

    /**
     * Flag which can be set to allow the tests to run or not.
     *
     * This is false by default and must be changed manually in order
     * to run.
     *
     * This is because the tests don't know if an appropriate monitor
     * is actually attached or not, so it's up to the person running
     * the tests to make this determination.
     * */
    private val shouldRunAutomatically = false

    /**
     * Connection to the appropriate serial port to use for testing.
     * */
    private lateinit var port: D253Monitor

    /**
     * Runs before each test.
     *
     * Retrieves the first appropriate USB device it can find and tries
     * to use it.
     *
     * Note that this doesn't actually probe the device to make sure if
     * it's a Dasung253 monitor or not. It just assumes that the first
     * writeable serial port belongs to an appropriate device.
     * */
    @BeforeTest
    fun setup(){

        // If the test isn't supposed to run, then just abort instead of
        // trying to find an appropriate device.
        if (!shouldRunAutomatically) return

        // TODO fix this and/or the getMonitors function to actually
        // probe the serial port and see if it's appropriate or not
        port = D253SerialPortList().getMonitors().first()

    }

    /**
     * Try to refresh the screen to clear ghosting.
     * */
    @Test
    fun testSerialPortClearGhosting(){

        if (!shouldRunAutomatically) return

        runTest {
            val value = port.clearGhosting()
            assertEquals(D253RequestCommand.SET_PARAMETER.value, value.cmd)
            assertEquals(D253RequestCommand.CLEAR_SCREEN.value, value.arg)
        }

    }

    /**
     * Try to query the screen for all of its settings.
     *
     * Checks the values of the settings to make sure they fall within the
     * expected ranges.
     * */
    @Test
    fun testSerialPortGetParameters(){

        if (!shouldRunAutomatically) return

        runTest {
            val params = port.getParameters()

            println("Threshold: ${params.threshold}")
            assert(params.threshold in D253Attribute.THRESHOLD.range)

            println("Light: ${params.light}")
            assert(params.light in D253Attribute.LIGHT.range)

            println("Speed: ${params.speed}")
            assert(params.speed in D253Attribute.SPEED.range)

            println("Frontlight: ${params.frontlight}")
            assert(params.frontlight in D253Attribute.FRONTLIGHT.range)

            println("Enhancement: ${params.enhancement}")
            assert(params.enhancement in D253Attribute.ENHANCEMENT.range)

            println("Display mode: ${params.displayMode}")
            assert(params.displayMode in D253DisplayMode.entries)

            println("MCU version: ${params.version}")
            assert(params.version in 0..100)

        }

    }

    /**
     * Tries to change the current display mode of the monitor.
     * */
    @Test
    fun testChangeDisplayMode(){

        if (!shouldRunAutomatically) return

        runTest {
            val success = port.setDisplayMode(D253DisplayMode.TEXT)
            assert(success)
        }

    }

}