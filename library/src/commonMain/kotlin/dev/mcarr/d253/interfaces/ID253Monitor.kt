package dev.mcarr.d253.interfaces

import dev.mcarr.d253.D253Response
import dev.mcarr.d253.data.D253Capabilities
import dev.mcarr.d253.data.D253Parameters
import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253DisplayMode
import dev.mcarr.d253.enums.D253RequestCommand
import kotlinx.datetime.Instant

/**
 * Interface which should be implemented by any classes which aim
 * to provide high-level functions for interacting with a Dasung253
 * monitor.
 *
 * Provides functions for changing all of the monitor's settings
 * and performing other common operations.
 * */
interface ID253Monitor {

    /**
     * Retrieves all known settings from the monitor.
     *
     * @return A D253Parameters data class object containing all
     * of the settings and their values
     *
     * @see D253Parameters
     * */
    suspend fun getParameters(): D253Parameters

    /**
     * Queries the monitor to find out which features it supports.
     *
     * @return A D253Capabilities data class object which contains
     * the features and their values indicating whether they're
     * supported or not.
     *
     * @see D253Capabilities
     * */
    suspend fun getCapabilities(): D253Capabilities

    /**
     * Refreshes the screen to clear any ghosting.
     *
     * @return A D253Response object containing the response
     * from the monitor
     * */
    suspend fun clearGhosting(): D253Response

    /**
     * Sets the RTC (real-time clock) of the monitor.
     *
     * Note: you can set the RTC, but not retrieve it.
     *
     * @return A D253Response object containing the response
     * from the monitor
     * */
    suspend fun setRtc(value: Instant): D253Response

    /**
     * Gets the value of the threshold setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getThreshold(): Int

    /**
     * Sets the value of the threshold setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setThreshold(value: Int): Boolean

    /**
     * Gets the value of the light setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getLight(): Int

    /**
     * Sets the value of the light setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setLight(value: Int): Boolean

    /**
     * Gets the value of the speed setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getSpeed(): Int

    /**
     * Sets the value of the speed setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setSpeed(value: Int): Boolean

    /**
     * Gets the value of the front light setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getFrontlight(): Int

    /**
     * Sets the value of the front light setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setFrontlight(value: Int): Boolean

    /**
     * Gets the value of the enhancement setting from the monitor.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getEnhancement(): Int

    /**
     * Sets the value of the enhancement setting on the monitor.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setEnhancement(value: Int): Boolean

    /**
     * Gets the monitor's color temperature.
     *
     * @return Integer representing the value of this setting
     * */
    suspend fun getTemperature(): Int

    /**
     * Sets the monitor's color temperature.
     *
     * @param value Value to set on the monitor
     *
     * @return True if successful
     * */
    suspend fun setTemperature(value: Int): Boolean

    /**
     * Gets the display mode of the monitor.
     *
     * @return D253DisplayMode enum value representing the value of this setting
     *
     * @see D253DisplayMode
     * */
    suspend fun getDisplayMode(): D253DisplayMode

    /**
     * Sets the monitor's display mode.
     *
     * @param mode New display mode to set
     *
     * @return True if successful
     *
     * @see D253DisplayMode
     * */
    suspend fun setDisplayMode(mode: D253DisplayMode): Boolean

    /**
     * Gets the model (mcu version) of the monitor.
     *
     * @return Integer representing the model number
     * */
    suspend fun getVersion(): Int

}