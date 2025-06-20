package dev.mcarr.d253

import dev.mcarr.d253.interfaces.ID253SerialPort
import dev.mcarr.usb.interfaces.ISerialPortWrapper
import dev.mcarr.d253.D253Constants.COM_STR_SIZE
import dev.mcarr.d253.enums.D253Attribute
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant

/**
 * Serial port implementation for interacting with Dasung253 monitors.
 *
 * Contains low-level functions for interacting with the serial port.
 *
 * If you want higher-level actions (eg. to change the display mode)
 * instead of direct port access, take a look at the D253Monitor class
 * instead.
 *
 * @param port The underlying serial port through which this class
 * sends and receives data
 *
 * @see D253Monitor
 * @see ISerialPortWrapper
 * */
class D253SerialPort(
    val port: ISerialPortWrapper
) : ID253SerialPort {

    /**
     * Default timeout of 10s for read operations
     * */
    private val readTimeout = 1000L

    /**
     * Default timeout of 10s for write operations
     * */
    val writeTimeout = 1000L

    override suspend fun write(
        cmd: D253Attribute,
        arg: Instant
    ): D253Response {
        val packet = D253Request.Builder()
            .setCommand(cmd)
            .setPayload(arg)
            .build()
        return write(packet)
    }

    override suspend fun write(
        arg: D253RequestValue
    ): D253Response {
        val packet = D253Request.Builder()
            .setCommandAndPayload(arg)
            .build()
        return write(packet)
    }

    override suspend fun write(packet: D253Request): D253Response {
        //println("Opening port")

        // "use" the port, so it opens and closes automatically
        return port.use {

            // Set the serial port baud rate
            it.setBaudRate(D253Constants.DS_BAUD_RATE)

            // The monitor seems to cache data sometimes, so flush first to make sure
            // the buffer is clear before reading/writing.
            flush()

            //println("Writing to port")
            // Write the packet to the port, with a timeout
            it.write(packet.data, writeTimeout)

            //println("Waiting for response")
            // Wait a brief moment for the monitor to process the data and make
            // a response available
            delay(10)

            // Read the responce
            val returnData = port.read(COM_STR_SIZE, readTimeout)

            //println("Parsing response")
            // Parse the response
            val response = D253Response.Builder()
                .setData(returnData)
                .build()

            // Wait before flushing
            delay(10)

            // Flush again afterwards, just to be sure
            flush()

            // Return the response
            response
        }
    }

    /**
     * Checks if the port has any data waiting to be read.
     *
     * If it does, the data is read and thrown away.
     *
     * This is repeated until the queue is clear.
     * */
    private suspend fun flush(){

        // Repeat as long as there's more data to read
        while (port.bytesAvailable() > 0){

            // Check how many bytes are available to read
            val extraData = port.bytesAvailable()

            //println("Flushing ${extraData} bytes")
            // Read that number of bytes, with a timeout
            port.read(extraData, readTimeout)

            // Wait briefly before repeating the loop
            delay(10)

        }
    }

}