package dev.mcarr.d253.interfaces

import dev.mcarr.d253.D253Request
import dev.mcarr.d253.D253RequestValue
import dev.mcarr.d253.D253Response
import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253RequestCommand
import kotlinx.datetime.Instant

/**
 * Interface describing the functions which a serial port class interacting
 * with a Dasung 253 monitor should implement.
 * */
interface ID253SerialPort {

    /**
     * Writes a request to the monitor's serial port and interprets the response.
     *
     * This function is for transmitting timestamps to the monitor, rather than simple
     * hex or integer values like other requests do.
     *
     * This is only needed for RTC requests.
     *
     * @param cmd Command to execute
     * @param arg Moment of time to use as the argument
     *
     * @return D253Response object containing the parsed data received from the monitor
     * */
    suspend fun write(cmd: D253Attribute, arg: Instant): D253Response

    /**
     * Writes a request to the monitor's serial port and interprets the response.
     *
     * @param arg Object which contains both the command to execute and the value to be transmitted
     *
     * @return D253Response object containing the parsed data received from the monitor
     * */
    suspend fun write(arg: D253RequestValue): D253Response

    /**
     * Writes a request to the monitor's serial port and interprets the response.
     *
     * @param packet Packet of data to write to the port
     *
     * @return D253Response object containing the parsed data received from the monitor
     * */
    suspend fun write(packet: D253Request): D253Response

}