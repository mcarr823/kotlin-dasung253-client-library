package dev.mcarr.d253.data

/**
 * Data class for storing the optional capabilities of a Dasung253 monitor.
 *
 * Not all Dasung253 monitors have the exact same features, so this class
 * keeps track of any features which might be present or absent on a given
 * model.
 *
 * @param supportsDisplayEnhancement Monitor supports the display enhancement
 * feature
 * @param hasFrontlight Monitor has a front light which can be turned on/off
 * */
data class D253Capabilities(
    val supportsDisplayEnhancement: Boolean,
    val hasFrontlight: Boolean
)