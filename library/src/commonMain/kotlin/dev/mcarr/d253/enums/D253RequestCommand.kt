package dev.mcarr.d253.enums

import dev.mcarr.d253.interfaces.ID253Command

/**
 * Defines the known commands which can be sent to a Dasung253 monitor.
 *
 * The commands in this enum do not include setter attributes.
 * ie. Attributes which also double up as commands.
 *
 * See the D253Attribute class for more information on those.
 *
 * In any case, a command is a hex string which is sent to the monitor
 * and proceeded by arguments related to the given command.
 * eg. GET_PARAMETER is proceeded by the paremeter which we want to
 * retrieve from the monitor.
 *
 * Commands are inserted into a builder class which handles the construction
 * of a data packet in the expected format.
 *
 * @param value Hex string value which identifies the given attribute.
 * This is a value known to the monitor and cannot be changed.
 * @param range Range of valid values for the attribute to be set to,
 * or -1 if the value doesn't have a defined range
 *
 * @see D253Attribute
 * @see dev.mcarr.d253.D253Request.Builder
 *
 * */
enum class D253RequestCommand(
    override val value: String,
    override val range: IntRange = -1..-1
) : ID253Command {

    CLEAR_SCREEN("03"), // aka. REFRESH

    // The range should match the possible values from D253Attribute
    GET_PARAMETER("0A", range = 1..18),

    SET_PARAMETER("F0")
}