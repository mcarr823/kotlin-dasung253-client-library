package dev.mcarr.d253

/**
 * Defines miscellaneous constants known to the monitor.
 * */
object D253Constants {

    /**
     * Fixed-length data packet size used by the monitor.
     * */
    const val COM_STR_SIZE = 24

    /**
     * Prefix used at the start of every communication to/from
     * the monitor.
     * */
    const val DS_COM_PREFIX = "5FF5"

    /**
     * Suffix used at the end of every communication to/from
     * the monitor.
     * */
    const val DS_COM_TAIL = "A0FA"

    /**
     * BAUD rate for the serial port connection.
     * */
    const val DS_BAUD_RATE = 115200

}