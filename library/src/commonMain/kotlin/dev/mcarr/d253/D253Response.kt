package dev.mcarr.d253

/**
 * Data packet received from the monitor.
 *
 * Dasung253 monitors send data in 24-byte chunks in a defined format.
 *
 * This class and its builder are used to parse and make sense
 * of the raw byte data sent by the monitor.
 *
 * All D253Response objects are compiled by using the internal
 * Builder class.
 *
 * @param cmd Command hex string
 * @param arg Argument hex string
 * @param data1 Data or secondary argument hex string
 * @param data2 Actual payload of the response
 * */
class D253Response internal constructor(
    var cmd: String,
    var arg: String,
    var data1: String,
    var data2: String
) {

    /**
     * Builder class for D253Response objects.
     *
     * Used to parse byte data received from the monitor.
     * */
    class Builder{

        /**
         * Byte data to parse.
         * */
        lateinit var data: ByteArray

        /**
         * Sets the byte data to parse.
         *
         * @param data Byte data received from the monitor
         *
         * @return The builder
         * */
        fun setData(data: ByteArray): Builder {
            this.data = data
            return this
        }

        /**
         * Validates and parses the byte data received from the monitor,
         * then converts it to a D253Response object.
         *
         * @return Compiled D253Response object
         *
         * @throws IllegalStateException If the data packet isn't valid
         * */
        fun build(): D253Response {

            // All data packets are expected to be a fixed length
            check(data.size == D253Constants.COM_STR_SIZE){ "Invalid packet length (${data.size})" }

            //println("Got bytes:")
            //data.forEachIndexed { i, b -> println("$i: ${b}") }

            // Decode the prefix (and other parts of the response).
            // It must be decoded, not just converted to a string.
            val prefix = data.decodeToString(0, 4)

            // All data packets must start with the expected byte string
            check(prefix == D253Constants.DS_COM_PREFIX){ "Invalid packet header ($prefix)" }

            // Parse the individual fields in the response
            val cmd = data.decodeToString(4, 6)
            val arg = data.decodeToString(6, 8)
            val data1 = data.decodeToString(8, 10)
            val data2 = data.decodeToString(10, 12)

            // Then put them in a response object
            return D253Response(cmd, arg, data1, data2)

        }

    }

}