package br.com.lucas.pomotimer.features.main.presentation.viewModel

import br.com.lucas.pomotimer.core.base.viewModel.BaseViewModel
import br.com.lucas.pomotimer.core.provider.alarmTones.AlarmTonesProvider
import br.com.lucas.pomotimer.core.provider.doNotDisturb.DoNotDisturbProvider
import br.com.lucas.pomotimer.core.provider.timer.CountdownTimerProvider
import br.com.lucas.pomotimer.core.provider.timer.CountdownTimerProvider.Status
import br.com.lucas.pomotimer.core.provider.timer.CountdownTimerProvider.Status.FINISHED
import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val countdownTimerProvider: CountdownTimerProvider,
    private val doNotDisturbProvider: DoNotDisturbProvider,
    private val alarmTonesProvider: AlarmTonesProvider
) : BaseViewModel() {

    val workTimeDurationInMillis = 10000L
    val hangOutTimeDurationInMillis = 5000L
    val onTimeUpdate = countdownTimerProvider.onTimeUpdate
    val onDoNotDisturbChange = doNotDisturbProvider.onDoNotDisturbChange

    private val _onGetAlarmTones = MutableStateFlow(emptyList<AlarmTone>())
    val onGetAlarmTones = _onGetAlarmTones.asStateFlow()

    init {
        setupOnTimeUpdateCollector()
    }

    fun getAlarmTones() {
        _onGetAlarmTones.update {
            alarmTonesProvider.getAll()
        }
    }
    fun startOrStopTimer() {
        if (onTimeUpdate.value == Status.STOPPED) startTimer() else stopTimer()
    }

    fun onSelectAlarmTone(alarmTone: AlarmTone) {
        _onGetAlarmTones.update { oldList ->
//            if (_onGetAlarmTones.subs)
            oldList.map { it.copy(isSelected = it.name == alarmTone.name) }
        }
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