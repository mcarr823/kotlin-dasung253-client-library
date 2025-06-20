package dev.mcarr.d253.interfaces

import dev.mcarr.d253.D253Monitor
import dev.mcarr.d253.D253SerialPort

/**
 * Interface defining the commands which any port-listing class should implement.
 * */
interface ID253SerialPortList {

    /**
     * Retrieves a list of available serial ports on the system.
     *
     * The results are low-level comm ports with more direct access for
     * reading and writing data.
     *
     * If you don't need that level of fine-grained control, you should
     * consider using the getMonitors() function instead.
     *
     * @return List of D253SerialPort objects
     *
     * @see D253SerialPort
     * @see getMonitors
     * */
    fun get(): List<ID253SerialPort>

    /**
     * Retrieves a list of available serial ports on the system.
     *
     * The results are objects which abstract away the nitty-gritty IO
     * comms and provide a simpler interface for interacting with the
     * monitor.
     *
     * @return List of D253Monitor objects
     *
     * @see D253Monitor
     * */
    fun getMonitors(): List<D253Monitor>

}