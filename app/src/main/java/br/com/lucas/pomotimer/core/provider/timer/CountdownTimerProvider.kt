package br.com.lucas.pomotimer.core.provider.timer

import br.com.lucas.pomotimer.core.extensions.ONE_SECOND_IN_MILLIS
import kotlinx.coroutines.flow.StateFlow

interface CountdownTimerProvider {

    val onTimeUpdate: StateFlow<Status>

    fun start(durationInMillis: Long, intervalInMillis: Long = Long.ONE_SECOND_IN_MILLIS)
    fun stop()

    sealed class Status {
        class RUNNING(val remainingTimeInMillis: Long, val durationInMillis: Long) : Status()
        data object STOPPED : Status()
        data object FINISHED : Status()
    }
}