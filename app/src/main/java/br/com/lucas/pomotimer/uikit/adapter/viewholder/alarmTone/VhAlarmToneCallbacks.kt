package br.com.lucas.pomotimer.uikit.adapter.viewholder.alarmTone

import br.com.lucas.pomotimer.features.main.domain.AlarmTone
import br.com.lucas.pomotimer.uikit.adapter.generic.GenericAdapterCallback

interface VhAlarmToneCallbacks : GenericAdapterCallback {

    fun onSelect(item: AlarmTone)
    fun onPlayStop(item: AlarmTone)

}