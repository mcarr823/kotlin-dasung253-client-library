package dev.mcarr.d253

import dev.mcarr.d253.D253SerialPort
import dev.mcarr.d253.interfaces.ID253SerialPortList
import dev.mcarr.usb.impl.SerialPortList
import dev.mcarr.usb.impl.SerialPortWrapper

/**
 * Serial port listing class based on the jserialcomm library.
 *
 * Provides functions for listing available serial ports on the system.
 * */
class D253SerialPortList : ID253SerialPortList {

    override fun get() =
        SerialPortList()
            .get()
            .map { D253SerialPort(it) }

    override fun getMonitors() = get().map { D253Monitor(it) }

}