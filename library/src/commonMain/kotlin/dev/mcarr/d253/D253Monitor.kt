package dev.mcarr.d253

import dev.mcarr.d253.data.D253Capabilities
import dev.mcarr.d253.data.D253Parameters
import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253DisplayMode
import dev.mcarr.d253.enums.D253RequestCommand
import dev.mcarr.d253.interfaces.ID253Command
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
) {

    /**
     * Retrieves all known settings from the monitor.
     *
     * @return A D253Parameters data class object containing all
     * of the settings and their values
     *
     * @see D253Parameters
     * */
    suspend fun getParameters(): D253Parameters {
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

    /**
     * Queries the monitor to find out which features it supports.
     *
     * @return A D253Capabilities data class object which contains
     * the features and their values indicating whether they're
     * supported or not.
     *
     * @see D253Capabilities
     * */
    suspend fun getCapabilities(): D253Capabilities {

        val mcu_version = getVersion()

        val supportsDisplayEnhancement = mcu_version in listOf(0x11, 0x31)
        val hasFrontLight = mcu_version in listOf(0x30, 0x31)

        return D253Capabilities(
            supportsDisplayEnhancement = supportsDisplayEnhancement,
            hasFrontlight = hasFrontLight
        )

    }

    /**
     * Refreshes the screen to clear any ghosting.
     *
     * @return A D253Response object containing the response
     * from the monitor
     * */
    suspend fun clearGhosting(): D253Response =
        setParameterValue(D253RequestCommand.CLEAR_SCREEN, 0)

    /**
     * Sets the RTC (real-time clock) of the monitor.
     *
     * Note: you can set the RTC, but not retrieve it.
     *
     * @return A D253Response object containing the response
     * from the monitor
     * */
    suspend fun setRtc(value: Instant): D253Response =
        setParameterValue(D253Attribute.RTC, value)

    /**
     * Gets the value of the threshold setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getThreshold(): Int =
        getParameterValue(D253Attribute.THRESHOLD)

    /**
     * Sets the value of the threshold setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setThreshold(value: Int): D253Response =
        setParameterValue(D253Attribute.THRESHOLD, value)

    /**
     * Gets the value of the light setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getLight(): Int =
        getParameterValue(D253Attribute.LIGHT)

    /**
     * Sets the value of the light setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setLight(value: Int): D253Response =
        setParameterValue(D253Attribute.LIGHT, value)

    /**
     * Gets the value of the speed setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getSpeed(): Int =
        getParameterValue(D253Attribute.SPEED)

    /**
     * Sets the value of the speed setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setSpeed(value: Int): D253Response =
        setParameterValue(D253Attribute.SPEED, value)

    /**
     * Gets the value of the front light setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getFrontlight(): Int =
        getParameterValue(D253Attribute.FRONTLIGHT)

    /**
     * Sets the value of the front light setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setFrontlight(value: Int): D253Response =
        setParameterValue(D253Attribute.FRONTLIGHT, value)

    /**
     * Gets the value of the enhancement setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getEnhancement(): Int =
        getParameterValue(D253Attribute.ENHANCEMENT)

    /**
     * Sets the value of the enhancement setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setEnhancement(value: Int): D253Response =
        setParameterValue(D253Attribute.ENHANCEMENT, value)

    /**
     * Gets the monitor's color temperature.
     *
     * @return Integer representing the value of this setting
     * */
    private suspend fun getTemperature(): Int =
        getParameterValue(D253Attribute.TEMPERATURE)

    /**
     * Sets the monitor's color temperature.
     *
     * @param value Value to set on the monitor
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253Response
     * */
    suspend fun setTemperature(value: Int): D253Response =
        setParameterValue(D253Attribute.TEMPERATURE, value)

    /**
     * Gets the display mode of the monitor.
     *
     * @return D253DisplayMode enum value representing the value of this setting
     *
     * @see D253DisplayMode
     * */
    private suspend fun getDisplayMode(): D253DisplayMode {
        val mode = getParameterValue(D253Attribute.DISPLAY_MODE)
        return D253DisplayMode.entries.first { it.value == mode }
    }

    /**
     * Sets the monitor's display mode.
     *
     * @param mode New display mode to set
     *
     * @return D253Response object which contains the raw response from
     * the monitor
     *
     * @see D253DisplayMode
     * @see D253Response
     * */
    suspend fun setDisplayMode(mode: D253DisplayMode): D253Response =
        setParameterValue(D253Attribute.DISPLAY_MODE, mode.value)

    /**
     * Gets the model (mcu version) of the monitor.
     *
     * @return Integer representing the model number
     * */
    private suspend fun getVersion(): Int =
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

}