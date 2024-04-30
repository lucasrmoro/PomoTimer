package br.com.lucas.pomotimer.core.provider.alarmTones

import br.com.lucas.pomotimer.features.main.domain.AlarmTone

interface AlarmTonesProvider {

    fun getAll(): List<AlarmTone>

}