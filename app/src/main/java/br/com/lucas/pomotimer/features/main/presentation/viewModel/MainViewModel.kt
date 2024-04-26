package br.com.lucas.pomotimer.features.main.presentation.viewModel

import br.com.lucas.pomotimer.core.base.viewModel.BaseViewModel
import br.com.lucas.pomotimer.core.extensions.FIVE_MINUTES_IN_MILLIS
import br.com.lucas.pomotimer.core.extensions.ONE_MINUTE_IN_MILLIS
import br.com.lucas.pomotimer.core.extensions.TWENTY_FIVE_MINUTES_IN_MILLIS
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status
import br.com.lucas.pomotimer.core.provider.CountdownTimerProvider.Status.FINISHED
import br.com.lucas.pomotimer.core.provider.CountdownTimerProviderImpl
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val countdownTimerProvider: CountdownTimerProvider = CountdownTimerProviderImpl()
) : BaseViewModel() {

    val workTimeDurationInMillis = 10000L
    val hangOutTimeDurationInMillis = 5000L
    val onTimeUpdate = countdownTimerProvider.onTimeUpdate

    init {
        setupOnTimeUpdateCollector()
    }

    fun startOrStopTimer() {
        if (onTimeUpdate.value == Status.STOPPED) startTimer() else stopTimer()
    }

    private fun startTimer() {
        countdownTimerProvider.start(durationInMillis = workTimeDurationInMillis)
    }

    private fun stopTimer() {
        countdownTimerProvider.stop()
    }

    private fun startHangOutTimer() {
        countdownTimerProvider.start(durationInMillis = hangOutTimeDurationInMillis)
    }

    private fun setupOnTimeUpdateCollector() {
        launch {
            onTimeUpdate.collect {
                if (it == FINISHED) startHangOutTimer()
            }
        }
    }
}