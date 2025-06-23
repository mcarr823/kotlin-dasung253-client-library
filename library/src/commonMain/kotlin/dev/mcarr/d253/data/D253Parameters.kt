package dev.mcarr.d253.data

import dev.mcarr.d253.enums.D253DisplayMode

/**
 * Data class for storing all of the queryable parameters of a Dasung253 monitor.
 *
 * ie. It stores the configurable settings (and model/version) of the monitor.
 *
 * Some features aren't supported by all Dasung253 models. They can be found
 * in the D253Capabilities data class.
 *
 * @param threshold
 * @param light Brightness level of the display
 * @param speed Value of the display speed setting. This is the setting level
 * of the monitor. It is NOT the refresh rate.
 * @param frontlight Brightness level of the monitor's front light, or
 * 0 if turned off. Not supported by all models.
 * @param enhancement Level of display enhancement being applied by the monitor
 * to the picture being displayed. Not supported by all models.
 * @param displayMode Current display mode of the monitor.
 * eg. Text mode, video mode, etc.
 * @param version MCU version of the monitor. Used to determine what features
 * the given monitor supports, since not all Dasung253 monitors have the same
 * features
 * @param temperature Color temperature of the display
 *
 * @see D253DisplayMode
 * @see D253Capabilities
 * */
data class D253Parameters(
    val threshold: Int,
    val light: Int,
    val speed: Int,
    val frontlight: Int,
    val enhancement: Int,
    val displayMode: D253DisplayMode,
    val version: Int,
    val temperature: Int
)