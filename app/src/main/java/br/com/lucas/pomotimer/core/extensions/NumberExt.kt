package br.com.lucas.pomotimer.core.extensions

import java.util.concurrent.TimeUnit

/**
 * Integer
 */
val Int.Companion.ZERO
    get() = 0

val Int.Companion.NINE
    get() = 9

fun Int?.orZero() = this ?: Int.ZERO

/**
 * Double
 * */

val Double.Companion.THIRTY_PERCENT
    get() = 0.3

val Double.Companion.SEVENTY_PERCENT
    get() = 0.7

/**
 * Long
 */

val Long.Companion.ZERO
    get() = 0L

val Long.Companion.ONE
    get() = 1L

val Long.Companion.FIVE
    get() = 5L

val Long.Companion.TWENTY_FIVE
    get() = 25L

val Long.Companion.ONE_SECOND_IN_MILLIS
    get() = TimeUnit.SECONDS.toMillis(Long.ONE)

val Long.Companion.ONE_MINUTE_IN_MILLIS
    get() = TimeUnit.MINUTES.toMillis(Long.ONE)

val Long.Companion.FIVE_MINUTES_IN_MILLIS
    get() = TimeUnit.MINUTES.toMillis(Long.FIVE)

val Long.Companion.TWENTY_FIVE_MINUTES_IN_MILLIS
    get() = TimeUnit.MINUTES.toMillis(Long.TWENTY_FIVE)

/**
 * Float
 * */

val Float.Companion.ZERO
    get() = 0f

val Float.Companion.ONE
    get() = 1f