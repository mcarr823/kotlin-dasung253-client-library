package dev.mcarr.d253

import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253RequestCommand
import dev.mcarr.d253.interfaces.ID253Command
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Compiled request which contains a data packet to be sent
 * to the monitor.
 *
 * All requests to the monitor should be sent as D253Request
 * objects, and all request objects should be compiled by
 * using the internal Builder class.
 *
 * @param data Raw byte data to send across the serial port
 * */
class D253Request internal constructor(
    val data: ByteArray
) {

    /**
     * Builder class for D253Request objects.
     *
     * Used to compile byte data requests in the specific format
     * which Dasung253 monitors expect.
     *
     * You must specify both a command to execute, and a payload
     * to send along with the command.
     * */
    class Builder{

        /**
         * Prefix expected by the monitor for every single data packet.
         * */
        private val prefix = D253Constants.DS_COM_PREFIX

        /**
         * Suffix expected by the monitor for every single data packet.
         * */
        private val tail = D253Constants.DS_COM_TAIL

        /**
         * Command to be sent to the monitor.
         * */
        private lateinit var command: String

        /**
         * Payload to be sent in the data packet.
         * */
        private lateinit var payload: String

        /**
         * Sets the command to be inserted into the data packet.
         *
         * @param command Command to run on the monitor
         *
         * @return The builder
         *
         * @see ID253Command
         * */
        fun setCommand(command: ID253Command): Builder {
            this.command = command.value
            return this
        }

        /**
         * Sets the command and payload to be inserted into the data packet.
         *
         * @param payload Command and payload to send to the monitor
         *
         * @return The builder
         *
         * @see D253RequestValue
         * */
        fun setCommandAndPayload(payload: D253RequestValue): Builder {
            this.command = payload.command
            setPayloadPadded(payload.hex)
            return this
        }

        /**
         * Sets the payload to be inserted into the data packet.
         *
         * An attribute can be used as either the command (to set that value)
         * or a payload (to get that value).
         *
         * So if you are using this function, you likely want to use
         * setCommand with the SET_PARAMETER command.
         *
         * @param payload Payload to send to the monitor
         *
         * @return The builder
         *
         * @see D253Attribute
         * */
        fun setPayload(payload: D253Attribute): Builder {
            setPayloadPadded(payload.value)
            return this
        }

        /**
         * Sets the payload to be inserted into the data packet after padding
         * it out to the desired length.
         *
         * Data packet payloads are 14 bytes in length, with most of the bytes being
         * character "0" (byte value 48).
         *
         * Most payloads are only 2 characters, so we need to pad them out to 14 bytes.
         *
         * @param payload Payload to pad and set
         * */
        private fun setPayloadPadded(payload: String){
            this.payload = payload.padEnd(14, '0')
        }

        /**
         * Sets the payload to be inserted into the data packet.
         *
         * This command is only used for RTC (real-time clock) requests.
         *
         * @param timestamp Moment in time which should be converted to
         * a timestamp and inserted into the payload
         *
         * @return The builder
         *
         * @see Instant
         * */
        fun setPayload(timestamp: Instant): Builder {

            // The monitor is likely in UTC timezone
            val ldt = timestamp.toLocalDateTime(TimeZone.UTC)

            // The payload for an RTC request is in the format:
            // HHmmssYYMMDD00
            // Each value must be zero-padded at the start
            this.payload =
                ldt.hour.zeroPad(2) +
                        ldt.minute.zeroPad(2) +
                        ldt.second.zeroPad(2) +
                        ldt.year.toString().substring(2) +
                        ldt.monthNumber.zeroPad(2) +
                        ldt.dayOfMonth.zeroPad(2) +
                        0.zeroPad(2)

            return this
        }

        /**
         * Compiles the request into a byte array and inserts it into
         * a D253Request object.
         *
         * This should be called last, after the command and payload have
         * already been set. Otherwise it will throw an exception.
         *
         * TODO write a unit test for this
         *
         * @return Compiled request object
         * */
        fun build(): D253Request {
            val buffer = prefix.encodeToByteArray() +
                    command.encodeToByteArray() +
                    payload.encodeToByteArray() +
                    tail.encodeToByteArray()
            //println("Sending: ${buffer.map { Char(it.toInt()) }}")
            return D253Request(buffer)
        }

        /**
         * Pads a string with zeros at the start.
         *
         * Used for padding out time values (eg. a number of seconds)
         * when performing RTC operations.
         *
         * eg. 9.zeroPad(2) would pad a value of 9 seconds to "09"
         *
         * @param length Total length of the resulting string
         *
         * @return Zero-padded string
         * */
        private fun Int.zeroPad(length: Int): String {
            return this.toString().padStart(length, '0')
        }

    }

}