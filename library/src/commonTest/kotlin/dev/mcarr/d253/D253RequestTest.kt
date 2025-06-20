package dev.mcarr.d253

import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253RequestCommand
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for request generation and data packet encoding.
 * */
class D253RequestTest {

    /**
     * Builds a request to set the THRESHOLD value to 1.
     *
     * The expected values are hard-coded and should never change
     * or fail.
     *
     * If this test fails, it means that one of the request builders
     * has been broken.
     * */
    @Test
    fun testBuilderThreshold(){

        val payload = D253RequestValue.Builder()
            .setCommand(D253Attribute.THRESHOLD)
            .setValue(1)
            .build()

        val packet = D253Request.Builder()
            .setCommandAndPayload(payload)
            .build()

        // Length check
        assertEquals(D253Constants.COM_STR_SIZE, packet.data.size)

        // Prefix
        assertEquals(53, packet.data[0])
        assertEquals(70, packet.data[1])
        assertEquals(70, packet.data[2])
        assertEquals(53, packet.data[3])

        // Type and command
        // Note that these are char codes, so 48 = 0 and 49 = 1
        assertEquals(48, packet.data[4])
        assertEquals(49, packet.data[5])
        assertEquals(48, packet.data[6])
        assertEquals(49, packet.data[7])

        // Payload
        // Should be empty, so every byte is 0
        assertEquals(48, packet.data[8])
        assertEquals(48, packet.data[9])
        assertEquals(48, packet.data[10])
        assertEquals(48, packet.data[11])
        assertEquals(48, packet.data[12])
        assertEquals(48, packet.data[13])
        assertEquals(48, packet.data[14])
        assertEquals(48, packet.data[15])
        assertEquals(48, packet.data[16])
        assertEquals(48, packet.data[17])
        assertEquals(48, packet.data[18])
        assertEquals(48, packet.data[19])

        // Tail
        assertEquals(65, packet.data[20])
        assertEquals(48, packet.data[21])
        assertEquals(70, packet.data[22])
        assertEquals(65, packet.data[23])

    }

    /**
     * Builds a request to set the RTC value to 0 ms since epoch.
     * ie. 1/1/1970
     *
     * The expected values are hard-coded and should never change
     * or fail.
     *
     * If this test fails, it means that one of the request builders
     * has been broken.
     *
     * This test is separate to the above THRESHOLD one because RTC
     * is the only parameter which uses a timestamp instead of an
     * integer value.
     *
     * It's also the only request which uses the "middle" bytes,
     * which are all 0 for other requests.
     * */
    @Test
    fun testBuilderRtcEpoch(){

        val instant = Instant.fromEpochMilliseconds(0)

        val packet = D253Request.Builder()
            .setCommand(D253Attribute.RTC)
            .setPayload(instant)
            .build()

        // Length check
        assertEquals(D253Constants.COM_STR_SIZE, packet.data.size)

        // Prefix
        assertEquals(53, packet.data[0])
        assertEquals(70, packet.data[1])
        assertEquals(70, packet.data[2])
        assertEquals(53, packet.data[3])

        // Command
        // Note that these are char codes, so 48 = 0
        assertEquals(48, packet.data[4])
        assertEquals(53, packet.data[5])

        // hour, minute, second
        assertEquals(48, packet.data[6])
        assertEquals(48, packet.data[7])
        assertEquals(48, packet.data[8])
        assertEquals(48, packet.data[9])
        assertEquals(48, packet.data[10])
        assertEquals(48, packet.data[11])

        // year, month, day (1970, 01, 01)
        assertEquals(55, packet.data[12])
        assertEquals(48, packet.data[13])
        assertEquals(48, packet.data[14])
        assertEquals(49, packet.data[15])
        assertEquals(48, packet.data[16])
        assertEquals(49, packet.data[17])

        // padding
        assertEquals(48, packet.data[18])
        assertEquals(48, packet.data[19])

        // Tail
        assertEquals(65, packet.data[20])
        assertEquals(48, packet.data[21])
        assertEquals(70, packet.data[22])
        assertEquals(65, packet.data[23])

    }

    /**
     * Another RTC builder request.
     *
     * This time around the test uses an actual time, instead of setting
     * the ms since epoch to 0.
     *
     * This test is to ensure that we're actually building the time correctly,
     * since the previous test could be writing 0 due to a failure of some kind.
     *
     * Also, some values are greater than 10 in this example, so this test
     * makes sure that double digit encoding and decoding is working for timestamps.
     * */
    @Test
    fun testBuilderRtcRealTime(){

        // 2025-06-18 08:15:23 (UTC time)
        val instant = Instant.fromEpochMilliseconds(1750234523640)

        val packet = D253Request.Builder()
            .setCommand(D253Attribute.RTC)
            .setPayload(instant)
            .build()

        // Length check
        assertEquals(D253Constants.COM_STR_SIZE, packet.data.size)

        // Prefix
        assertEquals(53, packet.data[0])
        assertEquals(70, packet.data[1])
        assertEquals(70, packet.data[2])
        assertEquals(53, packet.data[3])

        // Command
        // Note that these are char codes, so 48 = 0
        assertEquals(48, packet.data[4])
        assertEquals(53, packet.data[5])

        // hour, minute, second (08, 15, 23)
        assertEquals(48, packet.data[6])
        assertEquals(56, packet.data[7])
        assertEquals(49, packet.data[8])
        assertEquals(53, packet.data[9])
        assertEquals(50, packet.data[10])
        assertEquals(51, packet.data[11])

        // year, month, day (25, 06, 18)
        assertEquals(50, packet.data[12])
        assertEquals(53, packet.data[13])
        assertEquals(48, packet.data[14])
        assertEquals(54, packet.data[15])
        assertEquals(49, packet.data[16])
        assertEquals(56, packet.data[17])

        // padding
        assertEquals(48, packet.data[18])
        assertEquals(48, packet.data[19])

        // Tail
        assertEquals(65, packet.data[20])
        assertEquals(48, packet.data[21])
        assertEquals(70, packet.data[22])
        assertEquals(65, packet.data[23])

    }

}