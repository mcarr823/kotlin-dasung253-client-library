package dev.mcarr.d253

import dev.mcarr.d253.enums.D253Attribute
import dev.mcarr.d253.enums.D253RequestCommand
import dev.mcarr.d253.interfaces.ID253Command

/**
 * Compiled and validated value which can be used as an argument
 * in a D253Request object.
 *
 * This class and its builder perform value range validation
 * and data type conversions on the data before it gets inserted
 * into a D253Request object.
 *
 * Not all requests require this step, so D253RequestValue objects
 * aren't always needed.
 *
 * All D253RequestValue objects are compiled by using the internal
 * Builder class.
 *
 * @param command The command to be inserted into the data packet
 * @param hex The encoded hex string to be inserted into the data packet
 * as part of the payload
 * */
class D253RequestValue internal constructor(
    val command: String,
    val hex: String
) {

    /**
     * Builder class for D253RequestValue objects.
     *
     * Used to compile byte data requests in the specific format
     * which Dasung253 monitors expect, and to validate that the
     * value is appropriate for the given command.
     *
     * You must specify both a command to execute, and a value
     * to send along with the command.
     * */
    class Builder{

        /**
         * Range within which a given value must lie in order to
         * be considered valid.
         * */
        private lateinit var range: IntRange

        /**
         * Command to send in the data transmission.
         * */
        private var command: String = ""

        /**
         * Value to be validated and inserted into the payload.
         * */
        private var value: Int = 0

        /**
         * Sets the command to be inserted into the data packet,
         * and to be used for data validation.
         *
         * @param command Command to run, which also provides a
         * data validation range
         *
         * @return The builder
         *
         * @see ID253Command
         * */
        fun setCommand(command: ID253Command): Builder {
            this.command = command.value
            this.range = command.range
            return this
        }

        /**
         * Sets the value to be validated and converted, then
         * inserted into the data packet.
         *
         * @param value Integer value to be validated and converted
         *
         * @return The builder
         * */
        fun setValue(value: Int): Builder {
            this.value = value
            return this
        }

        /**
         * Validates the provided value against the provided command,
         * then converts the data as appropriate and packs it into
         * a D253RequestValue object.
         *
         * @return Compiled D253RequestValue object
         *
         * @throws IllegalArgumentException If the value is outside
         * of the allowed range
         * */
        fun build(): D253RequestValue {

            // Grab the min and max values of the defined range
            val min = range.first
            val max = range.last

            // If a range is defined, check if the value is inside of it
            if (min == -1 && max == -1){
                // No value to check
            }else if (value !in range){
                throw IllegalArgumentException("$command must be a value of $min-$max")
            }

            // Convert the value to a hex string, padded to 2 chars
            val hexString = value.toString(16).padStart(2, '0')

            // Return the resulting request value object
            return D253RequestValue(command, hexString)

        }

    }

}