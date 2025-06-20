package dev.mcarr.d253.enums

/**
 * Enum for the different display modes supported by Dasung253 monitors.
 *
 * @param value Integer value which is sent to/from the monitor when
 * querying/setting the given display mode. This is a value known to
 * the monitor and cannot be changed.
 * */
enum class D253DisplayMode(
    val value: Int
){
    AUTO(1),
    TEXT(2),
    GRAPHIC(3),
    VIDEO(4),
    UNKNOWN(5),
}