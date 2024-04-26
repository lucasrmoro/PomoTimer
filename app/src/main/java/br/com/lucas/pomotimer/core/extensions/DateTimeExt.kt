package br.com.lucas.pomotimer.core.extensions

import java.util.concurrent.TimeUnit

fun Long.millisToSeconds() = TimeUnit.MILLISECONDS.toSeconds(this)

fun Long.millisToMinutes() = TimeUnit.MILLISECONDS.toMinutes(this)

fun Long.minutesToSeconds() = TimeUnit.MINUTES.toSeconds(this)

fun Long.addTimeLeadingZero() =
    String.ZERO.plus(this.toString()).takeIf { this in Int.ZERO..Int.NINE } ?: this.toString()

fun Long.millisToMinutesAndSeconds(): String {
    val minutes = millisToMinutes()
    val seconds = millisToSeconds() - minutes.minutesToSeconds()
    return minutes.addTimeLeadingZero().plus(String.COLON).plus(seconds.addTimeLeadingZero())
}