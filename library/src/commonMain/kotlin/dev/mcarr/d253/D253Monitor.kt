package dev.mcarr.d253

import dev.mcarr.d253.data.D253Capabilities
import dev.mcarr.d253.data.D253Parameters
import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253DisplayMode
import dev.mcarr.d253.enums.D253RequestCommand
import dev.mcarr.d253.interfaces.ID253Command
import dev.mcarr.d253.interfaces.ID253Monitor
import dev.mcarr.usb.interfaces.ISerialPortWrapper
import kotlinx.datetime.Instant

/**
 * Class used for controlling a Dasung253 monitor.
 *
 * Provides functions for changing all of the monitor's settings
 * and performing other common operations.
 *
 * @param port Serial port through which this class interacts
 * with the monitor
 * */
class D253Monitor(
    private val port: D253SerialPort
) : ID253Monitor {

    override suspend fun getParameters(): D253Parameters {
        val threshold = getThreshold()
        val light = getLight()
        val speed = getSpeed()
        val frontlight = getFrontlight()
        val enhancement = getEnhancement()
        val displayMode = getDisplayMode()
        val version = getVersion()
        val temperature = getTemperature()
        return D253Parameters(
            threshold = threshold,
            light = light,
            speed = speed,
            frontlight = frontlight,
            enhancement = enhancement,
            displayMode = displayMode,
            version = version,
            temperature = temperature
        )
    }

    override suspend fun getCapabilities(): D253Capabilities {

        val mcu_version = getVersion()

        val supportsDisplayEnhancement = mcu_version in listOf(0x11, 0x31)
        val hasFrontLight = mcu_version in listOf(0x30, 0x31)

        return D253Capabilities(
            supportsDisplayEnhancement = supportsDisplayEnhancement,
            hasFrontlight = hasFrontLight
        )

    }

    override suspend fun clearGhosting(): D253Response =
        setParameterValue(D253RequestCommand.CLEAR_SCREEN, 0)

    override suspend fun setRtc(value: Instant): D253Response =
        setParameterValue(D253Attribute.RTC, value)

    override suspend fun getThreshold(): Int =
        getParameterValue(D253Attribute.THRESHOLD)

    override suspend fun setThreshold(value: Int): Boolean =
        setParameter(D253Attribute.THRESHOLD, value)

    override suspend fun getLight(): Int =
        getParameterValue(D253Attribute.LIGHT)

    override suspend fun setLight(value: Int): Boolean =
        setParameter(D253Attribute.LIGHT, value)

    override suspend fun getSpeed(): Int =
        getParameterValue(D253Attribute.SPEED)

    override suspend fun setSpeed(value: Int): Boolean =
        setParameter(D253Attribute.SPEED, value)

    override suspend fun getFrontlight(): Int =
        getParameterValue(D253Attribute.FRONTLIGHT)

    override suspend fun setFrontlight(value: Int): Boolean =
        setParameter(D253Attribute.FRONTLIGHT, value)

    override suspend fun getEnhancement(): Int =
        getParameterValue(D253Attribute.ENHANCEMENT)

    override suspend fun setEnhancement(value: Int): Boolean =
        setParameter(D253Attribute.ENHANCEMENT, value)

    override suspend fun getTemperature(): Int =
        getParameterValue(D253Attribute.TEMPERATURE)

    override suspend fun setTemperature(value: Int): Boolean =
        setParameter(D253Attribute.TEMPERATURE, value)

    override suspend fun getDisplayMode(): D253DisplayMode {
        val mode = getParameterValue(D253Attribute.DISPLAY_MODE)
        return D253DisplayMode.entries.first { it.value == mode }
    }

    override suspend fun setDisplayMode(mode: D253DisplayMode): Boolean =
        setParameter(D253Attribute.DISPLAY_MODE, mode.value)

    override suspend fun getVersion(): Int =
        getParameterValue(D253Attribute.VERSION)

    /**
     * Gets the value of the defined setting from the monitor.
     *
     * @param parameter The setting to retrieve from the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Attribute
     * @see D253Response
     * */
    private suspend fun getParameter(parameter: D253Attribute): D253Response{
        val payload = D253RequestValue.Builder()
            .setCommand(D253RequestCommand.GET_PARAMETER)
            .setValue(parameter.value.toInt(16))
            .build()
        return port.write(payload)
    }

    /**
     * Gets the value of the defined setting from the monitor.
     *
     * @param parameter The setting to retrieve from the monitor
     *
     * @return Integer representing the value of this setting
     *
     * @see D253Attribute
     * */
    private suspend fun getParameterValue(parameter: D253Attribute): Int{
        val value = getParameter(parameter)
        return value.data2.toUInt(16).toInt()
    }

    /**
     * Sets the value of the given setting on the monitor.
     *
     * @param command Setting to change
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see ID253Command
     * @see D253Response
     * */
    private suspend fun setParameterValue(command: ID253Command, value: Int): D253Response {
        val payload = D253RequestValue.Builder()
            .setCommand(command)
            .setValue(value)
            .build()
        return port.write(payload)
    }

    /**
     * Sets the value of the given setting on the monitor.
     *
     * Only used for changing the RTC.
     *
     * @param attribute Setting to change
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Attribute
     * @see D253Response
     * */
    private suspend fun setParameterValue(command: D253Attribute, value: Instant): D253Response {
        return port.write(command, value)
    }

    /**
     * Sets the value of the given setting on the monitor.
     *
     * @param command Setting to change
     * @param value Value to set on the monitor
     *
     * @return True if the response from the monitor matches the
     * expected return value, which should indicate that the
     * command was successful
     *
     * @see ID253Command
     * */
    private suspend fun setParameter(command: ID253Command, value: Int): Boolean {

        val response = setParameterValue(command, value)

        return response.cmd == command.value &&
                response.arg.toInt() == value &&
                response.data1.toInt() == 0 &&
                response.data2.toInt() == 0

    }

}