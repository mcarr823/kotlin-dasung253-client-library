package dev.mcarr.d253.enums

import dev.mcarr.d253.interfaces.ID253Command

/**
 * Defines the known attributes (aka. parameters or settings) of the monitor.
 *
 * Attributes can be used in two ways:
 * - the value can be sent as a standalone command instead of a D253RequestCommand
 * in order to SET the value, or
 * - the value can be used as the payload for a SET_PARAMETER command in order
 * to GET the value of that attribute.
 *
 * This dual-purpose nature of the values is why they are named differently
 * than the commands and parameters, and why they're separated into their own
 * class.
 *
 * @param value Hex string value which identifies the given attribute.
 * This is a value known to the monitor and cannot be changed.
 * @param range Range of valid values for the attribute to be set to,
 * or -1 if the value doesn't have a defined range
 *
 * @see D253RequestCommand
 * @see dev.mcarr.d253.data.D253Parameters
 *
 * */
enum class D253Attribute(
    override val value: String,
    override val range: IntRange = -1..-1
) : ID253Command {
    THRESHOLD("01", 1..9),
    DISPLAY_MODE("02", range = 1..D253DisplayMode.entries.size),
    SPEED("04", 1..5),
    RTC("05"),
    FRONTLIGHT("07", 0..3),
    TEMPERATURE("08", 0..100),
    LIGHT("09", 0..100),
    VERSION("10"),
    ENHANCEMENT("12", 0..1),
}