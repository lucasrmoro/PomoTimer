package br.com.lucas.pomotimer.core.provider

import br.com.lucas.pomotimer.core.extensions.ZERO
import br.com.lucas.pomotimer.core.extensions.millisToSeconds
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status.FINISHED
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status.RUNNING
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status.STOPPED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class CountdownTimerProviderImpl : CountdownTimerProvider {

    private val _onTimeUpdated = MutableStateFlow<Status>(STOPPED)
    override val onTimeUpdate = _onTimeUpdated.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null
    private var durationInMillis: Long = Long.ZERO
    private var intervalInMillis = Long.ZERO
    private var remainingTimeInMillis: Long = Long.ZERO

    override fun start(durationInMillis: Long, intervalInMillis: Long) {
        this.remainingTimeInMillis = durationInMillis
        this.durationInMillis = durationInMillis
        this.intervalInMillis = intervalInMillis
        reset()
        job = CoroutineScope(Dispatchers.Default).launch {
            (durationInMillis.millisToSeconds() downTo Long.ZERO).onEach {
                _onTimeUpdated.emit(RUNNING(remainingTimeInMillis, durationInMillis))
                remainingTimeInMillis -= intervalInMillis
                delay(intervalInMillis)
                if (it.shouldFinish()) {
                    reset()
                    _onTimeUpdated.emit(FINISHED)
                }
            }
        }
    }

    override fun stop() {
        reset()
        coroutineScope.launch {
            _onTimeUpdated.emit(STOPPED)
        }
    }

    private fun reset() {
        job?.cancel()
        job = null
    }

    private fun Long.shouldFinish() = this == Long.ZERO
}