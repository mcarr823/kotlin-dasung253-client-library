package dev.mcarr.d253.interfaces

/**
 * Interface defining the parameters required of any object which
 * purports to be a monitor command.
 *
 * This interface exists because "commands" come in two flavors:
 * - commands which tell the monitor to do something, and
 * - attributes which double up as setter commands.
 *
 * For example, the first type of command includes things like
 * an instruction to refresh the screen.
 * The command tells the monitor to perform a specific task.
 * These commands are defined in the D253RequestCommand enum.
 *
 * The second type relates to "settings" of the monitor, where
 * the setting itself can be either a value (to be used as the payload
 * in conjunction with a D253RequestCommand command) or a command
 * (whereby the setting name is the command, and the payload is the
 * value to set the setting to).
 *
 * @see dev.mcarr.d253.enums.D253RequestCommand
 * @see dev.mcarr.d253.enums.D253Attribute
 * */
interface ID253Command {

    /**
     * Hex string value which identifies the given command or attribute.
     *
     * This value is always a constant defined and known by the monitor,
     * and thus cannot be changed arbitrarily.
     * */
    val value: String

    /**
     * Range of valid values for the command, or -1 if not applicable.
     * */
    val range: IntRange

}